package edu.sky.luncher.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource("/test-application.properties")
//@Sql(value = {"/test-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ManagementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManagementRestController managementRestController;

    @Test
    void unauthorizedGetAll() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createRestaurant() {
    }

    @Test
    void addAdmin() {
    }

    @Test
    void removeAdmin() {
    }
}