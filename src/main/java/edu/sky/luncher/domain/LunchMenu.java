package edu.sky.luncher.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class LunchMenu extends AbstractBaseEntity {

    private String name;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lunch_menu_id")
    private Set<Meal> menuItems;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    public LunchMenu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Meal> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<Meal> menuItems) {
        this.menuItems = menuItems;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
