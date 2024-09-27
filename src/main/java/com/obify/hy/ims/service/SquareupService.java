package com.obify.hy.ims.service;

import com.obify.hy.ims.entity.square.SqCategory;
import com.obify.hy.ims.entity.square.SqProduct;
import com.obify.hy.ims.entity.square.SqSale;

import java.util.List;

public interface SquareupService {

    String processCategoryData(String sqToken);
    String processProductData(String sqToken);
    String processSalesData(String sqToken);

    List<SqCategory> getAllCategories();
    List<SqProduct> getAllProducts();
    List<SqSale> getAllSales();
}
