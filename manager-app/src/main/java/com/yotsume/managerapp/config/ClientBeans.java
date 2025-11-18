package com.yotsume.managerapp.config;

import com.yotsume.managerapp.client.RestClientProductRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductRestClientImpl productRestClient(
            @Value("${yotsume.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientProductRestClientImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());
    }
}
