package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.OrderEntity;
import com.nrtl.pizza.domain.PizzaEntity;
import com.nrtl.pizza.domain.UserEntity;
import com.nrtl.pizza.dto.OrderDto;
import com.nrtl.pizza.exception.EntityNotFoundException;
import com.nrtl.pizza.levels.Junior;
import com.nrtl.pizza.levels.Mid;
import com.nrtl.pizza.repository.OrderRepository;
import com.nrtl.pizza.repository.UserRepository;
import com.nrtl.pizza.security.SpringSecuritySecurityContextProvider;
import com.nrtl.pizza.task.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Cover OrderService with unit tests.
 * You can pick any known mocking framework
 */
@Mid(task = Assignment.TASK_01)
@Junior(task = Assignment.TASK_01)
@DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_01)
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    /**
     * Service under test.
     */

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SpringSecuritySecurityContextProvider securityContextProvider;

    @InjectMocks
    private OrderService orderService;

    public List<OrderEntity> orderEntities = new ArrayList<>();
    public OrderEntity orderEntity1 = new OrderEntity();
    public OrderEntity orderEntity2 = new OrderEntity();
    public UserEntity user = new UserEntity();
    public UserEntity adminClient = new UserEntity();
    public List<PizzaEntity> pizzas1 = new ArrayList<>();
    public List<PizzaEntity> pizzas2 = new ArrayList<>();
    public PizzaEntity pizza1 = new PizzaEntity();
    public PizzaEntity pizza2 = new PizzaEntity();

    @Test
    void shouldReturnAllClientOrders() {
        when(securityContextProvider.getUser()).thenReturn(
                new User("admin", "password", Collections.emptyList())
        );
        when(orderRepository.findOrdersByClientUsername(anyString())).thenReturn(orderEntities);
        List<OrderDto> result = orderService.getAllClientOrders();

        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(0).getAddress()).isEqualTo(orderEntity1.getAddress());
        assertThat(result.get(0).getPizzas().get(0)).isEqualTo(pizza1.getName());
        assertThat(result.get(1).getId()).isEqualTo(2);
        assertThat(result.get(1).getAddress()).isEqualTo(orderEntity2.getAddress());
        assertThat(result.get(1).getPizzas().get(0)).isEqualTo(pizza2.getName());
    }

    @Test
    void shouldReturnAllClientOrders_findByAddress() {
        when(orderRepository.findOrderByAddress(anyString())).thenReturn(orderEntities);
        List<OrderDto> result = orderService.findOrderByAddress(anyString());

        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getAddress()).isEqualTo(orderEntity1.getAddress());
        assertThat(result.get(1).getAddress()).isEqualTo(orderEntity2.getAddress());
    }

    @Test
    void shouldReturnAllClientOrders_getById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity1));
        OrderDto result = orderService.getById(anyInt());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(orderEntity1.getId());
        assertThat(result.getAddress()).isEqualTo(orderEntity1.getAddress());
    }

    @Test
    void shouldReturnAllClientOrders_getById_EntityNotFoundException() {
        when(orderRepository.findById(50)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
           orderService.getById(50);
        });
        assertThat(exception.getMessage()).isEqualTo("Entity with identifier [" + 50 + "] was not found.");

    }

    @Test
    void shouldReturnAllClientOrders_saveOrder() {
        OrderDto inputOrderDto = new OrderDto();
        inputOrderDto.setAddress("Test Address");
        when(securityContextProvider.getUser()).thenReturn(
                new User("admin", "password", Collections.emptyList())
        );
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity1);

        OrderDto result = orderService.saveOrder(inputOrderDto);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(orderEntity1.getId());
        assertThat(result.getAddress()).isEqualTo(orderEntity1.getAddress());
    }

    @BeforeEach
    void setup() {

        pizza1.setId(1);
        pizza1.setName("Pizza1");
        pizza1.setPrice(BigDecimal.valueOf(12.50));

        pizza2.setId(2);
        pizza2.setName("Pizza2");
        pizza2.setPrice(BigDecimal.valueOf(8.50));
        pizzas1.add(pizza1);
        pizzas2.add(pizza2);

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        adminClient.setId(1);
        adminClient.setUsername("admin");
        adminClient.setEnabled(true);
        adminClient.setAdmin(true);
        adminClient.setPassword("password");

        user.setId(2);
        user.setUsername("user");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setPassword("password");

        orderEntity1.setId(1);
        orderEntity1.setClient(adminClient);
        orderEntity1.setDateCreated(localDateTimeNow);
        orderEntity1.setAddress("address1");
        orderEntity1.setPizzas(pizzas1);

        orderEntity2.setId(2);
        orderEntity2.setClient(user);
        orderEntity2.setDateCreated(localDateTimeNow);
        orderEntity2.setAddress("address2");
        orderEntity2.setPizzas(pizzas2);

        orderEntities.add(orderEntity1);
        orderEntities.add(orderEntity2);
    }
}