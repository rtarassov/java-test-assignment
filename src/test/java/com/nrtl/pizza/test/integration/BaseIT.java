package com.nrtl.pizza.test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrtl.pizza.RestApplication;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig(classes = RestApplication.AppConfiguration.class)
class BaseIT {
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected final RequestPostProcessor USER_ADMIN = httpBasic("user-admin", "randompassword");
    protected final RequestPostProcessor USER_DISABLED = httpBasic("user-disabled", "randompassword");
    protected final RequestPostProcessor CLIENT_NRTL = httpBasic("client@nrtl.com", "randompassword");
    protected final RequestPostProcessor CLIENT_BENEDIKTAS = httpBasic("benediktas@nrtl.com", "randompassword");
    protected final RequestPostProcessor CLIENT_CANDIDATE = httpBasic("candidate@nrtl.com", "randompassword");

    @Autowired
    protected MockMvc mvc;

}