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

		List<PizzaEntity> pizzaEntities = allOrders.stream()
				.flatMap(order -> order.getPizzas().stream()).toList();

		long totalUniquePizzasOrdered = pizzaEntities.stream()
				.map(PizzaEntity::getId)
				.distinct()
				.count();

		Map<Integer, List<PizzaEntity>> pizzasGroupedById = pizzaEntities.stream()
				.collect(Collectors.groupingBy(PizzaEntity::getId));

		return pizzasGroupedById.values().stream()
				.map(pizzas -> {

					var totalRevenue = pizzas.stream()
							.mapToDouble(pizza -> pizza.getPrice().doubleValue()).sum();

					double orderedRatio = (double) pizzas.size() / totalUniquePizzasOrdered;
					double orderedRatioFraction = orderedRatio / 10;
					double orderedRatioTotal = orderedRatio + orderedRatioFraction;

					return PizzaOrderSummaryDto.PizzaOrders.builder()
							.name(pizzas.get(0).getName())
							.totalRevenue(totalRevenue)
							.orderedRatio(orderedRatioTotal)
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
