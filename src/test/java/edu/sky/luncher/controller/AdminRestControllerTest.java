package edu.sky.luncher.controller;

import edu.sky.luncher.domain.Meal;
import edu.sky.luncher.repository.MealRepository;
import edu.sky.luncher.util.exception.IllegalRequestDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.Util.asJsonString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test-application.properties")
class AdminRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MealRepository mealRepository;

    @Test
    @WithUserDetails("a")
    void getRestaurant() throws Exception {
        mockMvc.perform(get("/administration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name", is("rest1")));
    }


    @Test
    @WithUserDetails("a")
    void createMeal() throws Exception {
        Meal meal = new Meal("testMeal", 100L);
        when(mealRepository.save(Mockito.any(Meal.class))).thenReturn(meal);
        mockMvc.perform(post("/administration/200/meal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(meal)))
                .andExpect(status().isCreated());
        verify(mealRepository, times(1)).save(meal);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    @WithUserDetails("a")
    void createMealFail() throws Exception {
        Meal meal = new Meal("testMeal2", 100L);
        when(mealRepository.save(Mockito.any(Meal.class))).thenReturn(meal);
        NestedServletException thrownException = assertThrows(NestedServletException.class, () ->
                mockMvc.perform(post("/administration/201/meal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(meal))));
        assertTrue(thrownException.getCause() instanceof IllegalRequestDataException);
        assertThat(thrownException.getCause().getMessage(), is("Access denied"));
        verify(mealRepository, times(0)).save(meal);
        verifyNoMoreInteractions(mealRepository);
    }
}