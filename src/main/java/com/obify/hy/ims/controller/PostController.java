package com.obify.hy.ims.controller;

import com.obify.hy.ims.client.JSONPlaceholderFeign;
import com.obify.hy.ims.client.SquareupFeignClient;
import com.obify.hy.ims.client.model.*;
import com.obify.hy.ims.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class PostController {

    @Autowired
    JSONPlaceholderFeign jsonPlaceholderFeign;
    @Autowired
    SquareupFeignClient squareupFeignClient;

    @GetMapping("/posts")
    List<PostDTO> getPosts(){
        return jsonPlaceholderFeign.getPosts();
    }

    @GetMapping("/posts/{postId}")
    PostDTO getPostById(@PathVariable("postId") Long postId){
        return jsonPlaceholderFeign.getPostById(postId);
    }

//    @GetMapping("/locations")
//    ResponseEntity<LocationModelWrapper> getAllLocations(){
//       return squareupFeignClient.getAllLocations();
//    }

    @GetMapping("/categories")
    ResponseEntity<CategoryModelWrapper> getAllCategories(){
        return squareupFeignClient.getAllCategories("");
    }

    @GetMapping("/sales")
    ResponseEntity<SalesModelWrapper> getAllSales(){

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

        return squareupFeignClient.getFilteredSales("", sqm);
    }
}
