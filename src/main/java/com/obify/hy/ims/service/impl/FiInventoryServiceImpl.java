package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.OverviewResponseDTO;
import com.obify.hy.ims.entity.fi.FiIngredient;
import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import com.obify.hy.ims.entity.fi.FiProductIngredient;
import com.obify.hy.ims.entity.fi.Ingredient;
import com.obify.hy.ims.entity.square.SqSale;
import com.obify.hy.ims.repository.fi.FiIngredientRepository;
import com.obify.hy.ims.repository.fi.IngredientRepository;
import com.obify.hy.ims.repository.fi.PlannedInventoryRepository;
import com.obify.hy.ims.repository.fi.ProductIngredientRepository;
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

    @Override
    public OverviewResponseDTO inventoryOverview(OverviewRequestDTO requestDTO) {
        sqSalesRepository.deleteAllByMerchantId(requestDTO.getMerchantId());
        squareupService.processSalesData(requestDTO);
        List<SqSale> sales = sqSalesRepository.findAllByMerchantId(requestDTO.getMerchantId());
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
        OverviewResponseDTO dto = null;
        if(!qtyMap.isEmpty()) {
            dto = new OverviewResponseDTO();
            List<FiIngredient> ingredientList = new ArrayList<>();
            FiIngredient fii = null;
            for(Map.Entry<String, Float> mapOfIngredientQty : qtyMap.entrySet()) {
                fii = plannedInventoryRepository.findByIngredientId(mapOfIngredientQty.getKey());
                fii.setRemainingQty(fii.getRemainingQty() - mapOfIngredientQty.getValue());
                fii.setSalesToDtTime(LocalDateTime.parse(requestDTO.getEndAt()));
                fii = plannedInventoryRepository.save(fii);
                ingredientList.add(fii);
            }
            dto.setIngredient(ingredientList);
        }
        return dto;
    }
}
