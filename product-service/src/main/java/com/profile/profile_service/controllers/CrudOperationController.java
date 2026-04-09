package com.profile.profile_service.controllers;

import com.profile.profile_service.Exceptions.NoRecordFoundException;
import com.profile.profile_service.VO.VOProduct;
import com.profile.profile_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CrudOperationController {

    @Autowired
    public ProductService productService;

    @GetMapping("/api/product/getAllProducts")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        List<VOProduct> productList = productService.getAllProducts();
        if (productList.isEmpty()) {
            throw new NoRecordFoundException();
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Status", HttpStatus.OK);
        responseMap.put("Message", "Extracted product list");
        responseMap.put("ProductList", productList);

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @GetMapping("/api/product/getProductById/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        VOProduct productById = productService.getProductById(id);
        Map<String, Object> responseMap = new HashMap<>();
        if (null == productById) {
            responseMap.put("message", "Failed to find product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", "Extracted product");
        responseMap.put("product", productById);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @GetMapping("/api/product/getAllProductsById")
    public ResponseEntity<Map<String, Object>> getAllProductByIds(@RequestParam List<Long> productIds) {
        List<VOProduct> productsById = productService.getAllProductByIds(productIds);
        Map<String, Object> responseMap = new HashMap<>();
        if (null == productsById) {
            responseMap.put("message", "Failed to find product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", "Extracted product");
        responseMap.put("products", productsById);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @DeleteMapping("/api/product/delete-by-id/{id}")
    public ResponseEntity<Map<String, Object>> deleteProductById(@PathVariable Long id) {
        boolean productDeletedSuccessfully = productService.deleteProductById(id);
        Map<String, Object> responseMap = new HashMap<>();
        if (!productDeletedSuccessfully) {
            responseMap.put("message", "Failed to delete product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", String.format("Product with id %d deleted successfully", id));
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @PostMapping("/api/product/add-product")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody VOProduct product) {
        Map<String, Object> responseMap = new HashMap<>();
        if (null == product) {
            responseMap.put("message", "Please check message body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        VOProduct productAdded = productService.addProduct(product);
        responseMap.put("message", "Product Added Successfully");
        responseMap.put("Product", productAdded);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @PutMapping("/api/product/update-product/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @RequestBody VOProduct product) {
        Map<String, Object> responseMap = new HashMap<>();
        if (null == product) {
            responseMap.put("message", "Please check message body");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        VOProduct productUpdated = productService.updateProductById(id, product);
        if (null == productUpdated) {
            responseMap.put("message", "Failed to update product - product not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
        responseMap.put("message", "Product Updated Successfully");
        responseMap.put("Product", productUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }
}
