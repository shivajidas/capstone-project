package com.order.order_service.client.services;

import com.order.order_service.client.pojo.ProductPojo;
import com.order.order_service.exceptions.ApplicationUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductClientService {
    private static final Logger log = LoggerFactory.getLogger(ProductClientService.class);
    private final RestClient restClient;

    public ProductClientService(@Qualifier("productApiClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "getProductByIdFallback")
    @Retry(name = "productService")
    public ProductPojo getProductById(Long id){
        log.info("Attempting to fetch product with id: {}", id);
        ResponseEntity<String> message = restClient.get()
                .uri("/api/product/getProductById/%s",id)
                .retrieve().toEntity(String.class);
        String messageBody = message.getBody();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(messageBody);
            JsonNode productNode = root.get("product");
            return objectMapper.treeToValue(productNode, ProductPojo.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Product from response", e);
        }

    }

    /*@CircuitBreaker(name = "productService", fallbackMethod = "getProductByIdFallback")*/
    @CircuitBreaker(name = "productService", fallbackMethod = "getAllProductsByIdFallback")
    @Retry(name = "productService")
    public List<ProductPojo> getAllProductsById(List<Long> productIds){
        log.info("Attempting to fetch products with ids: {}", productIds);
        String uri = UriComponentsBuilder.fromUriString("/api/product/getAllProductsById")
                .queryParam("productIds", productIds).build().toUriString();
        ResponseEntity<String> message = restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(String.class);
        String messageBody = message.getBody();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(messageBody);
            JsonNode productNode = root.get("products");
            return objectMapper.treeToValue(productNode, new TypeReference<List<ProductPojo>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Product from response", e);
        }

    }

    // Fallback method for getProductById
    public ProductPojo getProductByIdFallback(Long id, Throwable throwable) {
        log.warn("Circuit breaker fallback triggered for getProductById. Product ID: {}, Error: {}", 
                id, throwable.getMessage());
        throw new ApplicationUnavailableException("price unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Fallback method for getAllProductsById
    public List<ProductPojo> getAllProductsByIdFallback(List<Long> productIds, Throwable throwable) {
        log.warn("Circuit breaker fallback triggered for getAllProductsById. Product IDs: {}, Error: {}", 
                productIds, throwable.getMessage());
        throw new ApplicationUnavailableException("price unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
