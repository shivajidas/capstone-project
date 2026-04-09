package com.profile.profile_service.services;

import com.profile.profile_service.VO.VOProduct;
import com.profile.profile_service.data.Product;
import com.profile.profile_service.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_returnsProductList() {
        Product product = Product.builder().productId(1L).name("Guitar").price(299.99).build();
        when(productRepo.findAll()).thenReturn(List.of(product));

        List<VOProduct> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Guitar", result.get(0).getName());
    }

    @Test
    void getProductById_found() {
        Product product = Product.builder().productId(1L).name("Piano").price(899.99).build();
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        VOProduct result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Piano", result.getName());
    }

    @Test
    void getProductById_notFound() {
        when(productRepo.findById(99L)).thenReturn(Optional.empty());

        VOProduct result = productService.getProductById(99L);

        assertNull(result);
    }

    @Test
    void addProduct_success() {
        Product saved = Product.builder().productId(1L).name("Violin").price(449.99).build();
        when(productRepo.save(any(Product.class))).thenReturn(saved);

        VOProduct input = VOProduct.builder().name("Violin").price(449.99).build();
        VOProduct result = productService.addProduct(input);

        assertNotNull(result);
        assertEquals(1L, result.getProductId());
    }

    @Test
    void deleteProductById_exists() {
        when(productRepo.existsById(1L)).thenReturn(true);

        boolean result = productService.deleteProductById(1L);

        assertTrue(result);
        verify(productRepo).deleteById(1L);
    }

    @Test
    void deleteProductById_notExists() {
        when(productRepo.existsById(99L)).thenReturn(false);

        boolean result = productService.deleteProductById(99L);

        assertFalse(result);
    }
}
