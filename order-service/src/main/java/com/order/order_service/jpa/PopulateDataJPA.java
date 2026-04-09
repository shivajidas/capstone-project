package com.order.order_service.jpa;

import com.order.order_service.entities.Customer;
import com.order.order_service.entities.OrderTable;
import com.order.order_service.entities.Product;
import com.order.order_service.repositories.CustomerRepository;
import com.order.order_service.repositories.OrderRepository;
import com.order.order_service.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.util.List;

@Component
@Slf4j
public class PopulateDataJPA {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @PostConstruct
    void afterStart(){
        if (orderRepository.count() > 0) {
            log.info("Orders already exist, skipping data initialization");
            return;
        }
        List<Customer> custList =
                List.of(
                        Customer.builder().name("Arijit").email("arijit@gmail.com").zipcode(100004).build(),
                        Customer.builder().name("Sonu").email("sonu@gmail.com").zipcode(100001).build(),
                        Customer.builder().name("KK").email("kk@gmail.com").zipcode(100002).build(),
                        Customer.builder().name("Jubin").email("jubin@gmail.com").zipcode(100003).build(),
                        Customer.builder().name("Shreya").email("shreya@gmail.com").zipcode(100004).build()
                );
        customerRepository.saveAll(custList);

        // Create products
        List<Product> productList = List.of(
                Product.builder().name("Guitar").description("Acoustic guitar with rosewood fretboard").price(299.99).quantityAvailable(50).build(),
                Product.builder().name("Piano").description("88-key digital piano").price(899.99).quantityAvailable(30).build(),
                Product.builder().name("Violin").description("Professional violin with case").price(449.99).quantityAvailable(40).build(),
                Product.builder().name("Drums").description("5-piece drum kit").price(599.99).quantityAvailable(25).build(),
                Product.builder().name("Flute").description("Silver-plated concert flute").price(199.99).quantityAvailable(60).build()
        );
        productRepository.saveAll(productList);

        // Create 10 orders
        long currentTimeMillis = System.currentTimeMillis();
        List<OrderTable> orderTableList = List.of(
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(0)).products(List.of(productList.get(0), productList.get(1))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(1)).products(List.of(productList.get(1), productList.get(2))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(2)).products(List.of(productList.get(2), productList.get(3))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(0)).products(List.of(productList.get(3), productList.get(4))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(1)).products(List.of(productList.get(4), productList.get(0))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(2)).products(List.of(productList.get(0), productList.get(1))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(0)).products(List.of(productList.get(1), productList.get(2))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(1)).products(List.of(productList.get(2), productList.get(3))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(2)).products(List.of(productList.get(3), productList.get(4))).build(),
                OrderTable.builder().date(new Date(currentTimeMillis)).customer(custList.get(0)).products(List.of(productList.get(4), productList.get(0))).build()
        );
        orderRepository.saveAll(orderTableList);

    }
}
