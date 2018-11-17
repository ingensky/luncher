package edu.sky.luncher.dto;

import com.fasterxml.jackson.annotation.JsonView;
import edu.sky.luncher.domain.LunchMenu;
import edu.sky.luncher.util.Views;

public class RestaurantWithLunchMenu {

    @JsonView(Views.Id.class)
    private Long restaurantId;

    @JsonView(Views.Name.class)
    private String name;


    @JsonView(Views.Body.class)
    private LunchMenu lunchMenu;

    @JsonView(Views.Body.class)
    private long rating;

    public RestaurantWithLunchMenu(Long restaurantId, String restaurantName,
                                   LunchMenu lunchMenu, long rating) {
        this.restaurantId = restaurantId;
        this.name = restaurantName;
        this.lunchMenu = lunchMenu;
        this.rating = rating;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LunchMenu getLunchMenu() {
        return lunchMenu;
    }

    public void setLunchMenu(LunchMenu lunchMenu) {
        this.lunchMenu = lunchMenu;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
