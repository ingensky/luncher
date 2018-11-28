package edu.sky.luncher.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.service.UserService;
import edu.sky.luncher.util.Views;
import edu.sky.luncher.util.exception.IllegalRequestDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test-application.properties")
@Sql(value = {"/test-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/test-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("m")
class ManagementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserService userService;


    @Test
    @WithUserDetails("a")
    void unauthorizedGetAll() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    void createRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant("testRest");
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(restaurant)))
                .andExpect(status().isCreated());
        assertEquals(3, restaurantRepository.findAll().size());

    }

    @Test
    @JsonView(Views.Body.class)
    @Transactional
    void addAdmin() throws Exception {
        User admin = userService.addAdmin(new User("testAdmin", "t"));
        mockMvc.perform(put("/restaurants/201/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(admin)))
                .andExpect(status().isOk());
        Restaurant byId = restaurantRepository.findById(201L).get();
        assertTrue(byId.getAdministrators().contains(admin));

    }

    @Test
    @Transactional
    void removeAdmin() throws Exception {
        mockMvc.perform(
                delete("/restaurants/200/admin/101"))
                .andExpect(status().isNoContent());
        Restaurant byId = restaurantRepository.findById(200L).get();
        assertEquals(1, byId.getAdministrators().size());

    }

    @Test
    void removeAdminFail() throws Exception {
        NestedServletException thrownException = assertThrows(NestedServletException.class, () -> mockMvc.perform(
                delete("/restaurants/201/admin/101"))
                .andExpect(status().isNoContent()));
        assertThat(thrownException.getCause() instanceof IllegalRequestDataException);
    }



    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }


    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}