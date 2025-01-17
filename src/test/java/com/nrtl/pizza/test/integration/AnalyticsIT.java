package com.nrtl.pizza.test.integration;

import com.nrtl.pizza.levels.Junior;
import com.nrtl.pizza.levels.Mid;
import com.nrtl.pizza.task.Assignment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyticsIT extends BaseIT {

    /**
     * We'd like to expose an analytics endpoint about each user.
     * For starters this endpoint should return two fields
     * 1. The id of the user
     * 2. The calculated average price for all the customers orders.
     */
    @Junior(task = Assignment.TASK_05)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_05)
    @Test
    public void shouldCalculateClientAverageExpensesOfAllOrders() throws Exception {
        String expectedResult = """
                  {
                	"userId": 3,
                	"averageOrderPrice": 13.82
                  }
                """;

        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/analytics/user")
                        .with(CLIENT_NRTL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    /**
     * We'd like to expose an analytics endpoint providing our pizza statistics.
     * The response should contain a list of pizzas and each entry should contain 3 fields:
     * 1. name - Name of the pizza.
     * 2. totalRevenue - Price sum of every occurrence where this pizza was ordered. Remember, an order might contain multiple different pizzas.
     * 3. orderedRatio - How often this pizza is ordered expressed in a ratio.
     */
    @Mid(task = Assignment.TASK_06)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_06)
    @Test
    public void shouldAnalysePizzaAndReturnAnalyticsForEach() throws Exception {
        String expectedResult = """
                  [
                  {
                	"name": "Capricciosa",
                	"totalRevenue": 7.99,
                	"orderedRatio": 0.11
                  },
                  {
                	"name": "Bismarck",
                	"totalRevenue": 15.98,
                	"orderedRatio": 0.22
                  },
                  {
                	"name": "Pepperoni",
                	"totalRevenue": 9.98,
                	"orderedRatio": 0.22
                  },
                  {
                	"name": "Bolognese",
                	"totalRevenue": 5.99,
                	"orderedRatio": 0.11
                  },
                  {
                	"name": "Margherita",
                	"totalRevenue": 7.99,
                	"orderedRatio": 0.11
                  },
                  {
                	"name": "Rustica",
                	"totalRevenue": 6.99,
                	"orderedRatio": 0.11
                  },
                  {
                	"name": "Calzone",
                	"totalRevenue": 7.99,
                	"orderedRatio": 0.11
                  },
                  {
                	"name": "Mimosa",
                	"totalRevenue": 11.99,
                	"orderedRatio": 0.11
                  },
                  {
                	"name": "Funghi",
                	"totalRevenue": 25.98,
                	"orderedRatio": 0.22
                  },
                  {
                	"name": "Tirolese",
                	"totalRevenue": 12.99,
                	"orderedRatio": 0.11
                  }
                  ]
                """;

        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/analytics/pizza")
                        .with(CLIENT_NRTL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
}
