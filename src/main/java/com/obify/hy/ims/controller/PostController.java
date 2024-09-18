package com.obify.hy.ims.controller;

import com.obify.hy.ims.client.JSONPlaceholderFeign;
import com.obify.hy.ims.client.SquareupFeignClient;
import com.obify.hy.ims.client.model.LocationModelWrapper;
import com.obify.hy.ims.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/locations")
    ResponseEntity<LocationModelWrapper> getAllLocations(){
       return squareupFeignClient.getAllLocations();
    }
}
