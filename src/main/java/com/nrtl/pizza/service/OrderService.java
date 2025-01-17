package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.OrderEntity;
import com.nrtl.pizza.domain.PizzaEntity;
import com.nrtl.pizza.dto.OrderDto;
import com.nrtl.pizza.exception.EntityNotFoundException;
import com.nrtl.pizza.repository.OrderRepository;
import com.nrtl.pizza.repository.UserRepository;
import com.nrtl.pizza.security.SpringSecuritySecurityContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	@Autowired
	private final SpringSecuritySecurityContextProvider securityContextProvider;

	public List<OrderDto> findOrderByAddress(String address) {
		String lowerAddress = address.toLowerCase();
		return orderRepository.findOrderByAddress(lowerAddress)
				.stream()
				.map(this::mapEntity)
				.collect(Collectors.toList());

	}

	public OrderDto saveOrder(final OrderDto orderDto) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setAddress(orderDto.getAddress());
		orderEntity.setClient(userRepository.findByUsername(securityContextProvider.getUser().getUsername()));
		orderEntity = orderRepository.save(orderEntity);

		return mapEntity(orderEntity);
	}

	public OrderDto getById(final Integer id) {
		return orderRepository.findById(id)
				.map(this::mapEntity)
				.orElseThrow(() -> new EntityNotFoundException(id));
	}

	public List<OrderDto> getAllClientOrders() {
		return orderRepository.findOrdersByClientUsername(securityContextProvider.getUser().getUsername())
				.stream()
				.map(this::mapEntity)
				.collect(Collectors.toList());
	}

	private OrderDto mapEntity(final OrderEntity orderEntity) {
		return OrderDto.builder()
				.id(orderEntity.getId())
				.address(orderEntity.getAddress())
				.pizzas(orderEntity.getPizzas().stream().map(PizzaEntity::getName).toList())
				.price(orderEntity.getPizzas().stream().map(PizzaEntity::getPrice)
						.toList().stream().reduce(BigDecimal.ZERO, BigDecimal::add))
				.build();
	}
}
