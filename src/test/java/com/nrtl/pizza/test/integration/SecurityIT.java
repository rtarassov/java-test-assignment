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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityIT extends BaseIT {

    /***
     * Currently disabled users are allowed to access their orders.
     * This needs to be fixed.
     */
    @Senior(task = Assignment.TASK_07)
    @Mid(task = Assignment.TASK_07)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_07)
    @Test
    public void shouldNotAllowToGetOrderListForDisabledUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/order/list")
                        .with(USER_DISABLED)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    /***
     * Currently regular client can access the internal reports.
     * This needs to be fixed. The reports should only be accessed by ADMIN users.
     * Admins are users with is_admin = 1 in user table
     */
    @Senior(task = Assignment.TASK_08)
    @Mid(task = Assignment.TASK_08)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_08)
    @Test
    public void shouldForbidClientToAccessReports() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/reports/weekly-pizza-orders")
                        .with(CLIENT_NRTL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }
}
