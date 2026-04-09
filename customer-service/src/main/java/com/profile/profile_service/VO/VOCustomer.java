package com.profile.profile_service.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VOCustomer {
    private Long customerId;
    private String name;
    private String email;
    private int zipcode;
}
