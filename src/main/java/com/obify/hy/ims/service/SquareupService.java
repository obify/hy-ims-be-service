package com.obify.hy.ims.service;

import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.OverviewResponseDTO;
import com.obify.hy.ims.entity.square.SqCategory;
import com.obify.hy.ims.entity.square.SqProduct;
import com.obify.hy.ims.entity.square.SqSale;

import java.util.List;

public interface SquareupService {

    String processCategoryData(String sqToken, String merchantId);
    String processProductData(String sqToken, String merchantId);
    String processSalesData(OverviewRequestDTO requestDTO);

    List<OverviewResponseDTO> inventoryOverview(OverviewRequestDTO requestDTO);
    List<SqCategory> getAllCategories();
    List<SqProduct> getAllProducts();
    List<SqSale> getAllSales();
}
