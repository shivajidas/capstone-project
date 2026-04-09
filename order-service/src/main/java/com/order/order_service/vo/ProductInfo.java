package com.order.order_service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductInfo {
    private Long productId;
    private String productName;
    private Double productPrice;
    private int quantity;
}
