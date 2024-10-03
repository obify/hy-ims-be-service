package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.OverviewResponseDTO;
import com.obify.hy.ims.entity.fi.FiIngredient;
import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import com.obify.hy.ims.entity.fi.FiProductIngredient;
import com.obify.hy.ims.entity.fi.Ingredient;
import com.obify.hy.ims.entity.square.RemainingInventory;
import com.obify.hy.ims.entity.square.SqSale;
import com.obify.hy.ims.repository.fi.*;
import com.obify.hy.ims.repository.square.SqProductRepository;
import com.obify.hy.ims.repository.square.SqSalesRepository;
import com.obify.hy.ims.service.FiInventoryService;
import com.obify.hy.ims.service.SquareupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FiInventoryServiceImpl implements FiInventoryService {

    @Autowired
    private SquareupService squareupService;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ProductIngredientRepository productIngredientRepository;
    @Autowired
    private PlannedInventoryRepository plannedInventoryRepository;
    @Autowired
    private SqSalesRepository sqSalesRepository;
    @Autowired
    private FiIngredientRepository fiIngredientRepository;
    @Autowired
    private RemainingInventoryRepository remainingInventoryRepository;

    @Override
    public OverviewResponseDTO inventoryOverview(OverviewRequestDTO requestDTO) {
        sqSalesRepository.deleteAllByMerchantId(requestDTO.getMerchantId());
        squareupService.processSalesData(requestDTO);
        OverviewResponseDTO dto = new OverviewResponseDTO();
        List<SqSale> sales = sqSalesRepository.findAllByMerchantId(requestDTO.getMerchantId());
        dto.setSales(sales);
        Map<String, Float> qtyMap = new HashMap<>();
        try {
            for (SqSale sale : sales) {
                Optional<FiProductIngredient> optFiProdIngredient = productIngredientRepository.findByProductName(sale.getProductName());
                if (optFiProdIngredient.isPresent()) {
                    for (FiIngredient fii : optFiProdIngredient.get().getIngredients()) {
                        if (qtyMap.get(fii.getIngredient()) == null) {
                            qtyMap.put(fii.getIngredient(), fii.getQuantity() * sale.getProductCountSold());
                        } else {
                            Float qty = qtyMap.get(fii.getIngredient());
                            qtyMap.put(fii.getIngredient(), qty + (fii.getQuantity() * sale.getProductCountSold()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!qtyMap.isEmpty()) {
            List<RemainingInventory> inventories = new ArrayList<>();
            RemainingInventory fii = null;
            for(Map.Entry<String, Float> mapOfIngredientQty : qtyMap.entrySet()) {
                FiIngredient fiidb = plannedInventoryRepository.findByIngredientId(mapOfIngredientQty.getKey());
                fii = new RemainingInventory();
                fii.setUsedQty(mapOfIngredientQty.getValue());
                fii.setIngredientId(fiidb.getIngredientId());
                fii.setIngredient(fiidb.getIngredient());
                fii.setRemainingQty(fiidb.getRemainingQty() - fii.getUsedQty());
                fii.setUnit(fiidb.getUnit());
                fii.setMerchantId(fiidb.getMerchantId());
                fii = remainingInventoryRepository.save(fii);
                inventories.add(fii);
            }
            dto.setFromDateTime(requestDTO.getStartAt());
            dto.setToDateTime(requestDTO.getEndAt());
            dto.setRemainingInventories(inventories);
        }
        return dto;
    }
}
