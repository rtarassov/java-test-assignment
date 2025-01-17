package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.OrderEntity;
import com.nrtl.pizza.domain.PizzaEntity;
import com.nrtl.pizza.domain.UserEntity;
import com.nrtl.pizza.dto.UserAnalyticsDto;
import com.nrtl.pizza.repository.OrderRepository;
import com.nrtl.pizza.repository.UserRepository;
import com.nrtl.pizza.security.SpringSecuritySecurityContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	private final UserRepository userRepository;
	private final SpringSecuritySecurityContextProvider securityContextProvider;
	private final OrderRepository orderRepository;

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
			averageOrderPrice = totalPrice.divide(BigDecimal.valueOf(pizzaEntities.size()), 2);
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
