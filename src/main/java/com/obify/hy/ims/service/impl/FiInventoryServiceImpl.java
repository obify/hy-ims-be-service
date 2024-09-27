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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FiInventoryServiceImpl implements FiInventoryService {

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
        List<SqSale> sales = sqSalesRepository.findAllByMerchantId(requestDTO.getMerchantId());
        Map<String, Integer> qtyMap = new HashMap<>();
        for(SqSale sale: sales){
           Optional<FiProductIngredient> optFiProdIngredient = productIngredientRepository.findByProductName(sale.getProductName());
           if(optFiProdIngredient.isPresent()){
               for(FiIngredient fii: optFiProdIngredient.get().getIngredients()){
                   if(qtyMap.get(fii.getIngredient()) == null) {
                       qtyMap.put(fii.getIngredient(), fii.getQuantity()*sale.getProductCountSold());
                   }else{
                       Integer qty = qtyMap.get(fii.getIngredient());
                       qtyMap.put(fii.getIngredient(), qty + (fii.getQuantity()* sale.getProductCountSold()));
                   }
               }
           }
        }
        OverviewResponseDTO dto = null;
        if(!qtyMap.isEmpty()) {
            dto = new OverviewResponseDTO();
            List<FiIngredient> ingredientList = new ArrayList<>();
            FiIngredient fiIngredient = null;
            for(Map.Entry<String, Integer> mapOfIngredientQty : qtyMap.entrySet()) {
                fiIngredient = new FiIngredient();
                fiIngredient.setIngredientId(mapOfIngredientQty.getKey());
                fiIngredient.setQuantity(mapOfIngredientQty.getValue());
                Optional<Ingredient> optIn = ingredientRepository.findById(mapOfIngredientQty.getKey());
                if(optIn.isPresent()){
                    fiIngredient.setIngredient(optIn.get().getName());
                    fiIngredient.setUnit(optIn.get().getUnitOfMeasurement());
                }
                List<FiPlannedInventory> plannedInventories = plannedInventoryRepository.findAll();
                for(FiPlannedInventory fip: plannedInventories){
                    for(FiIngredient fig: fip.getIngredients()){
                        if(fig.getIngredient().equals(mapOfIngredientQty.getKey())){
                            fiIngredient.setRemainingQty(fig.getQuantity()-fiIngredient.getQuantity());
                        }
                    }
                }
                ingredientList.add(fiIngredient);
            }
            dto.setIngredient(ingredientList);
        }
        return dto;
    }
}
