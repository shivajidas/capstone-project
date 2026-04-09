package com.profile.profile_service.services;

import com.profile.profile_service.VO.VOProduct;
import com.profile.profile_service.data.Product;
import com.profile.profile_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public List<VOProduct> getAllProducts() {
        Iterable<Product> products = productRepo.findAll();
        return StreamSupport.stream(products.spliterator(), false)
                .map(product -> VOProduct.builder()
                        .productId(product.getProductId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .quantityAvailable(product.getQuantityAvailable())
                        .build())
                .collect(Collectors.toList());
    }

    public VOProduct getProductById(Long id) {
        Optional<Product> productById = productRepo.findById(id);
        return productById.map(product -> VOProduct.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityAvailable(product.getQuantityAvailable())
                .build()).orElse(null);
    }

    public List<VOProduct> getAllProductByIds(List<Long> productIds){
        List<Product> products;
        products = StreamSupport.stream(productRepo.findAllById(productIds).spliterator(), false)
                .toList();
        return products.stream().map(
                product ->VOProduct.builder()
                            .productId(product.getProductId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .quantityAvailable(product.getQuantityAvailable()).build()

        ).toList();
    }

    public boolean deleteProductById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public VOProduct addProduct(VOProduct product) {
        Product productAdded = productRepo.save(Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityAvailable(product.getQuantityAvailable())
                .build());
        return VOProduct.builder()
                .productId(productAdded.getProductId())
                .name(productAdded.getName())
                .description(productAdded.getDescription())
                .price(productAdded.getPrice())
                .quantityAvailable(productAdded.getQuantityAvailable())
                .build();
    }

    public VOProduct updateProductById(Long id, VOProduct updatedVOProduct) {
        Optional<Product> productAfterUpdate = productRepo.findById(id).map(product -> {
            product.setName(updatedVOProduct.getName());
            product.setDescription(updatedVOProduct.getDescription());
            product.setPrice(updatedVOProduct.getPrice());
            product.setQuantityAvailable(updatedVOProduct.getQuantityAvailable());
            return productRepo.save(product);
        });
        return productAfterUpdate.map(product -> VOProduct.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityAvailable(product.getQuantityAvailable())
                .build()).orElse(null);
    }
}
