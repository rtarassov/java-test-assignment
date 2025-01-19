package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.OrderEntity;
import com.nrtl.pizza.domain.PizzaEntity;
import com.nrtl.pizza.domain.UserEntity;
import com.nrtl.pizza.dto.PizzaOrderSummaryDto;
import com.nrtl.pizza.dto.UserAnalyticsDto;
import com.nrtl.pizza.repository.OrderRepository;
import com.nrtl.pizza.repository.UserRepository;
import com.nrtl.pizza.security.SpringSecuritySecurityContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	private final UserRepository userRepository;
	private final SpringSecuritySecurityContextProvider securityContextProvider;
	private final OrderRepository orderRepository;

	public List<PizzaOrderSummaryDto.PizzaOrders> calculatePizzaAnalytics() {
		List<OrderEntity> allOrders = orderRepository.findAllOrders();

		List<PizzaEntity> allPizzas = allOrders.stream()
				.flatMap(order -> order.getPizzas().stream())
				.toList();

		int totalOrders = allOrders.size();

		Map<String, Long> pizzaOrderCountMap = allOrders.stream()
				.flatMap(order -> order.getPizzas().stream())
				.collect(Collectors.groupingBy(PizzaEntity::getName, Collectors.counting()));

		Map<String, Double> pizzaRevenueMap = allPizzas.stream()
				.collect(Collectors.groupingBy(PizzaEntity::getName,
						Collectors.summingDouble(pizza -> pizza.getPrice().doubleValue())));

		return pizzaOrderCountMap.entrySet().stream()
				.map(entry -> {

					String pizzaName = entry.getKey();
					long pizzaOrderCount = entry.getValue();
					double totalRevenue = pizzaRevenueMap.getOrDefault(pizzaName, 0.0);
					double orderedRatio = (double) pizzaOrderCount / totalOrders;

					return PizzaOrderSummaryDto.PizzaOrders.builder()
							.name(pizzaName)
							.totalRevenue(totalRevenue)
							.orderedRatio(Math.round(orderedRatio * 100.0) / 100.0)
							.build();
				})
				.toList();
	}


	public UserAnalyticsDto calculateUserAnalytics() {
		final UserEntity userEntity = getUserEntity();
		final int id = userEntity.getId();
		BigDecimal averageOrderPrice = BigDecimal.ZERO;

		List<OrderEntity> orderEntities = orderRepository.findOrdersByClientId(id);
		List<PizzaEntity> pizzaEntities = orderEntities.stream()
				.flatMap(order -> order.getPizzas().stream()).toList();

		BigDecimal totalPrice = pizzaEntities.stream()
				.map(PizzaEntity::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (!pizzaEntities.isEmpty()) {
			averageOrderPrice = totalPrice.divide(BigDecimal.valueOf(orderEntities.size()), 2);
		}

		return UserAnalyticsDto.builder()
				.userId(id)
				.averageOrderPrice(averageOrderPrice)
				.build();
	}

	private UserEntity getUserEntity() {
		final String username = securityContextProvider.getUser().getUsername();
		return userRepository.findByUsername(username);
	}
}
