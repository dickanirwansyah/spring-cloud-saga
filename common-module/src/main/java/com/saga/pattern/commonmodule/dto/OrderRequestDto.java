package com.saga.pattern.commonmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private Integer orderId;
}
