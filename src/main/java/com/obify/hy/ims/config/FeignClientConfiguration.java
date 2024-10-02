package com.obify.hy.ims.config;

import com.obify.hy.ims.repository.UserRepository;
import com.obify.hy.ims.security.services.UserDetailsImpl;
import com.obify.hy.ims.util.CommonUtil;
import feign.RequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    //@Value("${squareup.main.header}")
    //private String mainHeader;

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Square-Version", "2024-09-19");
            //requestTemplate.header("Authorization", "Bearer "+mainHeader);
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }
}
