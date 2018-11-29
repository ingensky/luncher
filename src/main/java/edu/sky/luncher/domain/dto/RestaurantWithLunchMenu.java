package edu.sky.luncher.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.sky.luncher.domain.LunchMenu;
import edu.sky.luncher.util.Views;

import java.util.Map;

import static edu.sky.luncher.util.Util.convertMealsToMap;

public class RestaurantWithLunchMenu {

    @JsonView(Views.Id.class)
    private Long restaurantId;

    @JsonView(Views.Name.class)
    private String restaurantName;

    @JsonView(Views.Body.class)
    private String lunchMenuOfDay;

    @JsonView(Views.Body.class)
    private Map<String, Long> meals;

    @JsonView(Views.Body.class)
    private long rating;

    public RestaurantWithLunchMenu(Long restaurantId, String restaurantName,
                                   LunchMenu lunchMenu, long rating) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.lunchMenuOfDay = lunchMenu.getName();
        this.meals = convertMealsToMap(lunchMenu);
        this.rating = rating;
    }


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLunchMenuOfDay() {
        return lunchMenuOfDay;
    }

    public void setLunchMenuOfDay(String lunchMenuOfDay) {
        this.lunchMenuOfDay = lunchMenuOfDay;
    }

    public Map<String, Long> getMeals() {
        return meals;
    }

    public void setMeals(Map<String, Long> meals) {
        this.meals = meals;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
