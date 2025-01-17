package com.nrtl.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Integer id;
	private String address;
	private List<String> pizzas;
	private BigDecimal price;
}
