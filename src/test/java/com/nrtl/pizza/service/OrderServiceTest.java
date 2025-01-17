package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.OrderEntity;
import com.nrtl.pizza.domain.UserEntity;
import com.nrtl.pizza.dto.OrderDto;
import com.nrtl.pizza.levels.Junior;
import com.nrtl.pizza.levels.Mid;
import com.nrtl.pizza.repository.OrderRepository;
import com.nrtl.pizza.task.Assignment;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Cover OrderService with unit tests.
 * You can pick any known mocking framework
 */
@Mid(task = Assignment.TASK_01)
@Junior(task = Assignment.TASK_01)
@DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_01)
@RequiredArgsConstructor
class OrderServiceTest {

    /**
     * Service under test.
     */

    @Mock
    private final OrderRepository orderRepository;

    private final OrderService orderService;
    public List<OrderEntity> orderEntities = new ArrayList<>();

    @BeforeEach
    void setup() {
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        UserEntity adminClient = new UserEntity();
        adminClient.setId(1);
        adminClient.setUsername("admin");
        adminClient.setEnabled(true);
        adminClient.setAdmin(true);
        adminClient.setPassword("password");
        UserEntity user = new UserEntity();
        user.setId(2);
        user.setUsername("user");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setPassword("password");
        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setId(1);
        orderEntity1.setClient(adminClient);
        orderEntity1.setDateCreated(localDateTimeNow);
        orderEntity1.setAddress("address1");
        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(2);
        orderEntity2.setClient(user);
        orderEntity2.setDateCreated(localDateTimeNow);
        orderEntity2.setAddress("adress2");
        orderEntities.add(orderEntity1);
        orderEntities.add(orderEntity2);
    }

    @Test
    void shouldReturnAllClientOrders() {
        when(orderRepository.findOrdersByClientId(anyString())).thenReturn(orderEntities);
        List<OrderDto> allClientOrders = orderService.getAllClientOrders();

        assertThat(allClientOrders.get(0).getId()).isEqualTo(1);
        assertThat(allClientOrders.get(0).getAddress()).isEqualTo("adress1");
        assertThat(allClientOrders.get(1).getId()).isEqualTo(2);
        assertThat(allClientOrders.get(1).getAddress()).isEqualTo("adress2");
        //add asserts
    }

    @Test
    void shouldReturnAllClientOrders2() {
        Assertions.fail("Implement me");
    }

    @Test
    void shouldReturnAllClientOrders3() {
        Assertions.fail("Implement me");
    }

    @Test
    void shouldReturnAllClientOrders4() {
        Assertions.fail("Implement me");
    }

    @Test
    void shouldReturnAllClientOrders5() {
        Assertions.fail("Implement me");
    }
}