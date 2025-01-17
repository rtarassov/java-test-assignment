package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.OrderEntity;
import com.nrtl.pizza.domain.PizzaEntity;
import com.nrtl.pizza.dto.PizzaOrderSummaryDto;
import com.nrtl.pizza.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingService {

	private final OrderRepository orderRepository;

	public PizzaOrderSummaryDto getSummary() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfCurrentWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		List<OrderEntity> thisWeekOrders = orderRepository.findOrdersFromThisWeek(startOfCurrentWeek, now);

		Map<String, Long> pizzaOrderCounts = thisWeekOrders.stream()
				.flatMap(order -> order.getPizzas().stream())
				.collect(Collectors.groupingBy(PizzaEntity::getName, Collectors.counting()));

		List<PizzaOrderSummaryDto.PizzaOrders> pizzaOrdersList = pizzaOrderCounts.entrySet().stream()
				.map(entry -> PizzaOrderSummaryDto.PizzaOrders.builder()
						.name(entry.getKey())
						.quantity(entry.getValue())
						.build())
				.toList();

		return PizzaOrderSummaryDto.builder()
				.pizzaOrders(pizzaOrdersList)
				.build();
	}

}
