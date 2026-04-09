package com.order.order_service.client.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPojo {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantityAvailable;
}
