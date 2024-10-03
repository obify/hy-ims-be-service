package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.client.SquareupFeignClient;
import com.obify.hy.ims.client.model.*;
import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.OverviewResponseDTO;
import com.obify.hy.ims.entity.Product;
import com.obify.hy.ims.entity.User;
import com.obify.hy.ims.entity.square.SqCategory;
import com.obify.hy.ims.entity.square.SqProduct;
import com.obify.hy.ims.entity.square.SqSale;
import com.obify.hy.ims.repository.UserRepository;
import com.obify.hy.ims.repository.square.SqCategoryRepository;
import com.obify.hy.ims.repository.square.SqProductRepository;
import com.obify.hy.ims.repository.square.SqSalesRepository;
import com.obify.hy.ims.security.services.UserDetailsImpl;
import com.obify.hy.ims.service.SquareupService;
import com.obify.hy.ims.util.CommonUtil;
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
    @Autowired
    private UserRepository userRepository;

    @Override
    //@Async
    //@Scheduled(cron = "${cron.expression.category}")
    public String processCategoryData(String sqToken, String merchantId) {
        log.info(" processCategoryData job started ");
        ResponseEntity<CategoryModelWrapper> re = squareupFeignClient.getAllCategories("Bearer "+sqToken);
        if(re.getStatusCode().is2xxSuccessful()){
            sqCategoryRepository.deleteAllByMerchantId(merchantId);
            CategoryModelWrapper cmw = re.getBody();
            if(!Objects.isNull(cmw.getObjects())){
                cmw.getObjects().
                        forEach((categoryModel -> {
                            Optional<SqCategory> optSqCategory = sqCategoryRepository.findAllByNameContaining(categoryModel.getCategory_data().getName());
                            if(optSqCategory.isEmpty()){
                                SqCategory sqCategory = new SqCategory();
                                sqCategory.setId(categoryModel.getId());
                                sqCategory.setMerchantId(merchantId);
                                sqCategory.setName(categoryModel.getCategory_data().getName());
                                sqCategoryRepository.save(sqCategory);
                            }
                        }));
            }
        }
        log.info(" processCategoryData job ended ");
        return "Success";
    }

    @Override
    //@Async
    //@Scheduled(cron = "${cron.expression.product}")
    public String processProductData(String sqToken, String merchantId) {
        User user = userRepository.findById(merchantId).get();
        System.out.println("product started");
        ProductRequestModel requestModel = new ProductRequestModel();
        requestModel.setLimit(100);
        requestModel.setProduct_types(Arrays.asList("REGULAR"));
        List<SqCategory> categories = sqCategoryRepository.findAllByMerchantId(merchantId);
        List<String> catIds = new ArrayList<>();
        for(SqCategory category: categories){
            catIds.add(category.getId());
        }
        //requestModel.setCategory_ids(Arrays.asList("7RTN6W3G7MZRHAHPLUHKM6F7"));
        requestModel.setCategory_ids(catIds);
        requestModel.setSort_order("ASC");
        requestModel.setEnabled_location_ids(Arrays.asList(user.getLocationId()));

        ResponseEntity<ProductModelWrapper> re = squareupFeignClient.getAllProducts("Bearer "+sqToken, requestModel);
        if(re.getStatusCode().is2xxSuccessful()){
            productRepository.deleteAllByMerchantId(merchantId);
            ProductModelWrapper pmw = re.getBody();
            if(pmw.getItems() != null){
                pmw.getItems().forEach((item)->{
                    SqProduct product = new SqProduct();
                    product.setId(item.getId());
                    product.setMerchantId(merchantId);
                    product.setName(item.getItem_data().getName());
                    try{
                        if(null != item.getItem_data() &&
                                null != item.getItem_data().getEcom_image_uris() &&
                                !item.getItem_data().getEcom_image_uris().isEmpty()){
                            product.setImageUrl(item.getItem_data().getEcom_image_uris().get(0));
                        }else{
                            product.setImageUrl("https://images.unsplash.com/photo-1610513320995-1ad4bbf25e55?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8bm90JTIwYXZhaWxhYmxlfGVufDB8fDB8fHww");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    productRepository.save(product);
                });
            }
        }
        System.out.println("product ended");
        return "Success";
    }

    @Override
    //@Async
    //@Scheduled(cron = "${cron.expression.sales}")
    public String processSalesData(OverviewRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getMerchantId()).get();
        System.out.println("sale started");
        SalesQueryStateFilter sqsf = new SalesQueryStateFilter();
        sqsf.setStates(List.of("COMPLETED"));

        SalesQueryRequestModel sqrm = new SalesQueryRequestModel();

        StartAtModel sam = new StartAtModel();
        sam.setStart_at(requestDTO.getStartAt());

        ClosedAtFilter caf = new ClosedAtFilter();
        caf.setStart_at(requestDTO.getStartAt());
        if(requestDTO.getEndAt() == null){
            caf.setEnd_at(LocalDateTime.now().toString());
        }else{
            caf.setEnd_at(requestDTO.getEndAt());
        }

        SalesDateTimeFilter sdtf = new SalesDateTimeFilter();
        sdtf.setClosed_at(caf);

        SalesQueryFilterModel sqfm = new SalesQueryFilterModel();
        sqfm.setDate_time_filter(sdtf);
        sqfm.setState_filter(sqsf);

        sqrm.setFilter(sqfm);

        SalesRequestModel sqm = new SalesRequestModel();
        sqm.setReturn_entries(true);
        sqm.setLocation_ids(List.of(user.getLocationId()));
        sqm.setQuery(sqrm);
        ResponseEntity<SalesModelWrapper> re = null;
        try {
            re = squareupFeignClient.getFilteredSales("Bearer "+requestDTO.getToken() ,sqm);
        }catch (Exception ex){
            ex.printStackTrace();
        }

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
                    sqSale.setUpdatedAt(LocalDateTime.now());
                    sqSale.setProductName(mapData.getKey());
                    if(sqSale.getProductName() != null){
                        Optional<SqProduct> optProduct = productRepository.findFirstByName(sqSale.getProductName());
                        if(optProduct.isPresent() && optProduct.get().getImageUrl() != null){
                            sqSale.setImageUrl(optProduct.get().getImageUrl());
                        }else{
                            sqSale.setImageUrl("https://images.unsplash.com/photo-1610513320995-1ad4bbf25e55?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8bm90JTIwYXZhaWxhYmxlfGVufDB8fDB8fHww");
                        }
                    }else{
                        sqSale.setProductName("NOT AVAILABLE");
                        sqSale.setImageUrl("https://images.unsplash.com/photo-1610513320995-1ad4bbf25e55?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8bm90JTIwYXZhaWxhYmxlfGVufDB8fDB8fHww");
                    }
                    sqSale.setProductCountSold(mapData.getValue());
                    sqSale.setMerchantId(requestDTO.getMerchantId());
                    sqSalesRepository.save(sqSale);
                }
            }
        }
        System.out.println("sale ended");
        return "Success";
    }

    @Override
    public List<OverviewResponseDTO> inventoryOverview(OverviewRequestDTO requestDTO) {
        return List.of();
    }

    @Override
    public List<SqCategory> getAllCategories() {
        return sqCategoryRepository.findAll();
    }

    @Override
    public List<SqProduct> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<SqSale> getAllSales() {
        return sqSalesRepository.findAll();
    }

}
