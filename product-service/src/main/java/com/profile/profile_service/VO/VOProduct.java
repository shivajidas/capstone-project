package com.profile.profile_service.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VOProduct {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantityAvailable;
}
