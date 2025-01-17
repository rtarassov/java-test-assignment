package com.nrtl.pizza.test.integration;

import com.nrtl.pizza.dto.OrderDto;
import com.nrtl.pizza.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/***
 * Ensure that order creation flow is not broken.
 */
public class SmokeTestIT extends BaseIT {

    @Autowired
    OrderRepository orderRepository;

    /***
     * Tests a simple order submit flow.
     * It should always pass!
     */
    @Test
    void shouldPassOrderFlow() throws Exception {
        OrderDto dto = OrderDto.builder().address("Rinktines g. 6, Vilnius").build();

        String orderJson = OBJECT_MAPPER.writeValueAsString(dto);
        MvcResult orderCreateResult = mvc.perform(
                        MockMvcRequestBuilders
                                .request(HttpMethod.POST, "/order")
                                .with(CLIENT_CANDIDATE)
                                .content(orderJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        OrderDto createdOrder = OBJECT_MAPPER.readValue(orderCreateResult.getResponse().getContentAsString(), OrderDto.class);
        orderJson = OBJECT_MAPPER.writeValueAsString(createdOrder);

        mvc.perform(
                        MockMvcRequestBuilders
                                .request(HttpMethod.GET, "/order/{id}", createdOrder.getId())
                                .with(CLIENT_CANDIDATE)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(orderJson));

        orderRepository.deleteById(createdOrder.getId());
    }
}
