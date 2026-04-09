package com.order.order_service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VOOrder {
    private Long id;
    private Date date;
    private Long customerId;
    private Double orderValue;
    private List<Long> productIds;
    private List<ProductInfo> productInfoList;

}
