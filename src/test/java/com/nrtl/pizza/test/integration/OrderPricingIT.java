package com.nrtl.pizza.test.integration;

import com.nrtl.pizza.levels.Mid;
import com.nrtl.pizza.task.Assignment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderPricingIT extends BaseIT {

    private static final String ASSERT_FOR_PRICING_WITH_PRICING_MIGRATION = """
            [
              {
                "id": 6,
                "address": "Gedimino pr. 11, Vilnius",
                "pizzas": [
                  "Funghi"
                ],
                "price": 12.99
              },
              {
                "id": 5,
                "address": "Tilto g. 5, Vilnius",
                "pizzas": [
                  "Mimosa"
                ],
                "price": 11.99
              },
              {
                "id": 4,
                "address": "Kauno g. 3, Vilnius",
                "pizzas": [
                  "Bismarck"
                ],
                "price": 7.99
              },
              {
                "id": 3,
                "address": "Vilniaus g. 2, Vilnius",
                "pizzas": [
                  "Rustica",
                  "Calzone"
                ],
                "price": 14.98
              },
              {
                "id": 2,
                "address": "Traku g. 1, Vilnius",
                "pizzas": [
                  "Bolognese",
                  "Margherita"
                ],
                "price": 13.98
              },
              {
                "id": 1,
                "address": "Rinktines g. 5, Vilnius",
                "pizzas": [
                  "Capricciosa",
                  "Bismarck",
                  "Pepperoni"
                ],
                "price": 20.97
              }
            ]
            """;
    private static final boolean STRICT_MATCHING = true;

    /**
     * Currently orders do not contain pricing.
     * To figure out the price of an order you'd need to lookup the price of each pizza in an order and sum it up manually.
     * There's another issue with the current setup - the price of a pizza might change. This would lead to historic pricing no longer being accurate!
     *
     * We'd like you to do the following tasks:
     * 1. Add a new column on the order_ table. (update flyway schema file)
     * 2. Update the response of the /order/list endpoint, to include this newly added pricing field.
     * 3. Add default 9.99 pricing for all orders. (update flyway data file)
     * 4. (bonus) Use pizza table pricing information to update old orders with accurate pricing. (update flyway data file)
     */
    @Mid(task = Assignment.TASK_04)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_04)
    @Test
    public void shouldDefineOrderPricing() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/order/list")
                        .with(CLIENT_NRTL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(ASSERT_FOR_PRICING_WITH_PRICING_MIGRATION, STRICT_MATCHING));
    }
}
