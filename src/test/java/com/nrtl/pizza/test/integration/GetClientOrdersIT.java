package com.nrtl.pizza.test.integration;

import com.nrtl.pizza.levels.Junior;
import com.nrtl.pizza.levels.Mid;
import com.nrtl.pizza.levels.Senior;
import com.nrtl.pizza.task.Assignment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetClientOrdersIT extends BaseIT {

    /**
     * We'd like to return to our API consumers an additional node containing more information about the order.
     * Please include an array of pizza names in the response.
     */
    @Junior(task = Assignment.TASK_02)
    @Mid(task = Assignment.TASK_02)
    @Senior(task = Assignment.TASK_02)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_02)
    @Test
    void shouldGetClientOrders() throws Exception {
        String expectedResult = """
                [
                  {
                    "id": 6,
                    "address": "Gedimino pr. 11, Vilnius",
                    "pizzas": [
                      "Funghi"
                    ]
                  },
                  {
                    "id": 5,
                    "address": "Tilto g. 5, Vilnius",
                    "pizzas": [
                      "Mimosa"
                    ]
                  },
                  {
                    "id": 4,
                    "address": "Kauno g. 3, Vilnius",
                    "pizzas": [
                      "Bismarck"
                    ]
                  },
                  {
                    "id": 3,
                    "address": "Vilniaus g. 2, Vilnius",
                    "pizzas": [
                      "Rustica",
                      "Calzone"
                    ]
                  },
                  {
                    "id": 2,
                    "address": "Traku g. 1, Vilnius",
                    "pizzas": [
                      "Bolognese",
                      "Margherita"
                    ]
                  },
                  {
                    "id": 1,
                    "address": "Rinktines g. 5, Vilnius",
                    "pizzas": [
                      "Capricciosa",
                      "Bismarck",
                      "Pepperoni"
                    ]
                  }
                ]
                """;

        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/order/list")
                        .with(CLIENT_NRTL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    /***
     * Currently the address is a free form text field so the casing might not match.
     * Please find all the orders delivered to the same address.
     * Make sure to cover two scenarios
     * 1. Where address is incomplete (partial match)
     * 2. Where address contains different casing (case insensitivity)
     *
     * Candidate can choose either sql or code approaches. Talk about performance.
     */
    @Mid(task = Assignment.TASK_03)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_03)
    @Test
    public void shouldFindOrdersWithPartialCaseInsensitiveAddressMatch() throws Exception {
        String expectedResult = """
                [
                  {
                    "id": 8,
                    "address": "Benedikto g. 5, VILNIUS",
                    "pizzas": [
                      "Tirolese"
                    ]
                  },
                  {
                    "id": 7,
                    "address": "Benedikto g. 5, Vilnius",
                    "pizzas": [
                      "Pepperoni"
                    ]
                  }
                ]
                """;

        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/order/find-by-address")
                        .content("Benedikto g. 5, Vilniu")
                        .contentType(MediaType.TEXT_PLAIN)
                        .with(CLIENT_BENEDIKTAS)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
}
