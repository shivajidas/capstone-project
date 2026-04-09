package com.order.order_service.client.services;

import com.order.order_service.client.pojo.CustomerPojo;
import com.order.order_service.client.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerClientService {
    private final RestClient restClient;

    public CustomerClientService(@Qualifier("customerApiClient") RestClient externalApiClient) {
        this.restClient = externalApiClient;
    }

    public CustomerPojo getCustomerById(Long id){
        ResponseEntity<String> message = restClient.get()
                .uri("/api/customer/getCustomerById/".concat(String.valueOf(id)))
                .retrieve().toEntity(String.class);
        String messageBody = message.getBody();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(messageBody);
            JsonNode customerNode = root.get("customer");
            return objectMapper.treeToValue(customerNode, CustomerPojo.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Customer from response", e);
        }

    }

}
