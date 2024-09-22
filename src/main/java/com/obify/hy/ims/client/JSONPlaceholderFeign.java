package com.obify.hy.ims.client;

import com.obify.hy.ims.config.FeignClientConfiguration;
import com.obify.hy.ims.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "jsonplaceholder", url = "${jsonplaceholder.url}", configuration = FeignClientConfiguration.class)
public interface JSONPlaceholderFeign {

    @GetMapping("/posts")
    List<PostDTO> getPosts();

    @GetMapping("/posts/{postId}")
    PostDTO getPostById(@PathVariable("postId") Long postId);
}
