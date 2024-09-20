package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.client.SquareupFeignClient;
import com.obify.hy.ims.client.model.*;
import com.obify.hy.ims.entity.square.SqCategory;
import com.obify.hy.ims.entity.square.SqProduct;
import com.obify.hy.ims.entity.square.SqSale;
import com.obify.hy.ims.repository.square.SqCategoryRepository;
import com.obify.hy.ims.repository.square.SqProductRepository;
import com.obify.hy.ims.repository.square.SqSalesRepository;
import com.obify.hy.ims.service.SquareupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@EnableAsync
@Service
@Slf4j
public class SquareupServiceImpl implements SquareupService {

    @Autowired
    private SquareupFeignClient squareupFeignClient;
    @Autowired
    private SqCategoryRepository sqCategoryRepository;
    @Autowired
    private SqSalesRepository sqSalesRepository;
    @Autowired
    private SqProductRepository productRepository;

    @Override
    @Async
    @Scheduled(cron = "${cron.expression.category}")
    public void processCategoryData() {
        log.info(" processCategoryData job started ");
        ResponseEntity<CategoryModelWrapper> re = squareupFeignClient.getAllCategories();
        if(re.getStatusCode().is2xxSuccessful()){
            CategoryModelWrapper cmw = re.getBody();
            if(!Objects.isNull(cmw.getObjects())){
                cmw.getObjects().
                        forEach((categoryModel -> {
                            Optional<SqCategory> optSqCategory = sqCategoryRepository.findAllByNameContaining(categoryModel.getCategory_data().getName());
                            if(optSqCategory.isEmpty()){
                                SqCategory sqCategory = new SqCategory();
                                sqCategory.setLocationId("LVSTZCJXY793K");
                                sqCategory.setId(categoryModel.getId());
                                sqCategory.setName(categoryModel.getCategory_data().getName());
                                sqCategoryRepository.save(sqCategory);
                            }
                        }));
            }
        }
        log.info(" processCategoryData job ended ");
    }

    @Override
    @Async
    @Scheduled(cron = "${cron.expression.product}")
    public void processProductData() {
        System.out.println("product started");
        ResponseEntity<ProductModelWrapper> re = squareupFeignClient.getAllProducts();
        if(re.getStatusCode().is2xxSuccessful()){
            ProductModelWrapper pmw = re.getBody();
            if(pmw.getItems() != null){
                pmw.getItems().forEach((item)->{
                    SqProduct product = new SqProduct();
                    product.setId(item.getId());
                    product.setName(item.getItem_data().getName());
                    productRepository.save(product);
                });
            }
        }
        System.out.println("product ended");
    }

    @Override
    @Async
    @Scheduled(cron = "${cron.expression.sales}")
    public void processSalesData() {
        System.out.println("sale started");
        SalesQueryStateFilter sqsf = new SalesQueryStateFilter();
        sqsf.setStates(List.of("COMPLETED"));

        SalesQueryRequestModel sqrm = new SalesQueryRequestModel();

        StartAtModel sam = new StartAtModel();
        sam.setStart_at(LocalDateTime.now().toString());

        ClosedAtFilter caf = new ClosedAtFilter();
        //caf.setStart_at(sam);

        SalesDateTimeFilter sdtf = new SalesDateTimeFilter();
        //sdtf.setClosed_at(caf);

        SalesQueryFilterModel sqfm = new SalesQueryFilterModel();
        //sqfm.setDate_time_filter(sdtf);
        sqfm.setState_filter(sqsf);

        sqrm.setFilter(sqfm);

        SalesRequestModel sqm = new SalesRequestModel();
        sqm.setReturn_entries(true);
        sqm.setLocation_ids(List.of("LNM38YF22M4V0"));
        sqm.setQuery(sqrm);
        ResponseEntity<SalesModelWrapper> re = squareupFeignClient.getFilteredSales(sqm);

        if(re.getStatusCode().is2xxSuccessful()) {
            SalesModelWrapper smw = re.getBody();
            Map<String, Integer> salesCountMap = new HashMap<>();
            if (!Objects.isNull(smw.getOrder_entries())) {
                smw.getOrder_entries().
                        forEach(salesModel -> {
                            if(!Objects.isNull(salesModel.getLine_items())){
                                salesModel.getLine_items().forEach((sale)->{
                                    if(null == salesCountMap.get(sale.getName())){
                                        salesCountMap.put(sale.getName(), Integer.parseInt(sale.getQuantity()));
                                    }else{
                                        salesCountMap.put(sale.getName(), salesCountMap.get(sale.getName())+Integer.parseInt(sale.getQuantity()));
                                    }
                                });
                            }
                        });

            }
            if(!salesCountMap.isEmpty()){
                for(Map.Entry<String, Integer> mapData: salesCountMap.entrySet()){
                    SqSale sqSale = new SqSale();
                    sqSale.setProductName(mapData.getKey());
                    sqSale.setProductCountSold(mapData.getValue());
                    sqSalesRepository.save(sqSale);
                }
            }
        }
        System.out.println("sale ended");
    }

    @Override
    public void calculateTotalProductSales() {

    }
}
