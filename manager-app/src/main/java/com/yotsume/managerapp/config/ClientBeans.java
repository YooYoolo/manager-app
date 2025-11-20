package com.yotsume.managerapp.config;

import com.yotsume.managerapp.client.RestClientProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductRestClient productRestClient(
            @Value("${yotsume.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("${yotsume.services.catalogue.username:}") String catalogueUsername,
            @Value("${yotsume.services.catalogue.password:}") String cataloguePassword){
        return new RestClientProductRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(
                        catalogueUsername,
                        cataloguePassword))
                .build());
    }
}