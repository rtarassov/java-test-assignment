package com.nrtl.pizza.test.integration;

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

public class ReportingIT extends BaseIT {

    public static final String ASSERT_FOR_WEEKLY_ORDERED_PIZZAS = """
            {
              "pizzaOrders": [
                {
                  "name": "Bolognese",
                  "quantity": 1
                },
                {
                  "name": "Rustica",
                  "quantity": 1
                },
                {
                  "name": "Margherita",
                  "quantity": 1
                },
                {
                  "name": "Tirolese",
                  "quantity": 1
                },
                {
                  "name": "Calzone",
                  "quantity": 1
                },
                {
                  "name": "Capricciosa",
                  "quantity": 1
                },
                {
                  "name": "Mimosa",
                  "quantity": 1
                },
                {
                  "name": "Bismarck",
                  "quantity": 2
                },
                {
                  "name": "Funghi",
                  "quantity": 2
                },
                {
                  "name": "Pepperoni",
                  "quantity": 2
                }
              ]
            }
                """;

    /***
     * Business would like to have ability to see how many of each pizza were ordered last week.
     * Return a response which contains a list of all pizza names, along with the count how many times it was ordered.
     */
    @Senior(task = Assignment.TASK_09)
    @Mid(task = Assignment.TASK_09)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_09)
    @Test
    public void shouldReturnLastWeekOrderedPizzaQuantities() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/reports/weekly-pizza-orders")
                        .with(USER_ADMIN)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json(ASSERT_FOR_WEEKLY_ORDERED_PIZZAS));
	}

}
