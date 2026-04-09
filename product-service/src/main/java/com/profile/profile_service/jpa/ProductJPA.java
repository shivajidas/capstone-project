/*
package com.profile.profile_service.jpa;

import com.profile.profile_service.data.Product;
import com.profile.profile_service.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProductJPA {
    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    void afterStart(){
        List<Product> productList =
                List.of(
                        Product.builder().name("Laptop").description("High-performance laptop").price(999.99).quantityAvailable(50).build(),
                        Product.builder().name("Smartphone").description("Latest smartphone model").price(699.99).quantityAvailable(100).build(),
                        Product.builder().name("Headphones").description("Wireless noise-canceling headphones").price(249.99).quantityAvailable(200).build(),
                        Product.builder().name("Tablet").description("10-inch tablet with stylus").price(449.99).quantityAvailable(75).build(),
                        Product.builder().name("Smartwatch").description("Fitness tracking smartwatch").price(199.99).quantityAvailable(150).build()
                );
        productRepository.saveAll(productList);

    }

}
*/
