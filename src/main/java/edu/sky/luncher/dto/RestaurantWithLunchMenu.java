package edu.sky.luncher.dto;

import edu.sky.luncher.domain.LunchMenu;

public class RestaurantWithLunchMenu {

    private Long restaurantId;

    private String restaurantName;

    private LunchMenu lunchMenu;

    private long rating;

    public RestaurantWithLunchMenu(Long restaurantId, String restaurantName,
                                   LunchMenu lunchMenu, long rating) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.lunchMenu = lunchMenu;
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
