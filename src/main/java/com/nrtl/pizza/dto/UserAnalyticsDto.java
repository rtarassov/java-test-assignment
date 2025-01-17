package com.nrtl.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnalyticsDto {
	private Integer userId;
	private BigDecimal averageOrderPrice;
}
