package com.nrtl.pizza.test.integration;

import com.nrtl.pizza.domain.UserEntity;
import com.nrtl.pizza.levels.Mid;
import com.nrtl.pizza.levels.Senior;
import com.nrtl.pizza.repository.UserRepository;
import com.nrtl.pizza.task.Assignment;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class PerformanceIT extends BaseIT {

    private static final long MAX_USERS = 1500000;

    @Autowired
    private UserRepository userRepository;

    /***
     * Current execution logic is not efficiently loading user's orders with larger amount of user records.
     * Please improve performance that it would be executed in the given execution range.
     */
    @Senior(task = Assignment.TASK_10)
    @Mid(task = Assignment.TASK_10)
    @DisplayName(Assignment.TaskDescription.TASK_DESCRIPTION_10)
    @Test
    public void shouldReturnOrderInTime() {

        UserEntity firstUser = createUser(0L);
        //warmup
        getAverageCustomerOrderLoadTime(firstUser);
        getAverageCustomerOrderLoadTime(firstUser);

        //initial measure
        long loadTimeInitial = getAverageCustomerOrderLoadTime(firstUser);

        // data loading
        LongStream.range(1, MAX_USERS).forEach(this::createUser);

        // measure after data load
        UserEntity user = createUser(MAX_USERS + 1);
        long loadTime = getAverageCustomerOrderLoadTime(user);

        double loadRatio = (double) loadTime / (double) loadTimeInitial;
        log.info("Execution time: " + loadTime + " / " + loadTimeInitial + ":" + loadRatio);

        assertTrue(loadRatio < 1.1);
    }

    private long getAverageCustomerOrderLoadTime(UserEntity user) {
        int iterations = 1000;
        long sum = LongStream.range(0, iterations)
                .map(
                        i -> {
                            long iterNanoTime = System.nanoTime();
                            findCustomerOrders(httpBasic(user.getUsername(), user.getPassword()));
                            long loadTime = System.nanoTime() - iterNanoTime;
                            log.info("Load time: " + loadTime);
                            return loadTime;
                        })
                .sum();
        log.info("Total time of : " + sum);
        return (sum / iterations);
    }

    @SneakyThrows
    private UserEntity createUser(Long i) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("user" + i);
        userEntity.setEnabled(true);
        userEntity.setAdmin(false);
        userEntity.setPassword("pass" + i);
        return userRepository.save(userEntity);
    }

    @SneakyThrows
    private void findCustomerOrders(RequestPostProcessor auth) {
        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, "/order/list")
                        .with(auth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
