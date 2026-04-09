package com.order.order_service.client.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerPojo {
    private Long customerId;
    private String name;
    private String email;
    private int zipcode;
}
