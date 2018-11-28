package edu.sky.luncher.controller;

import edu.sky.luncher.domain.LunchMenu;
import edu.sky.luncher.domain.Meal;
import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.LunchMenuRepository;
import edu.sky.luncher.repository.MealRepository;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

import static edu.sky.luncher.util.Util.checkAccess;
import static edu.sky.luncher.util.Util.getUri;

@RestController
@RequestMapping(AdminRestController.REST_URL)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {

    static final String REST_URL = "/administration";

    private RestaurantRepository restaurantRepository;
    private MealRepository mealRepository;
    private LunchMenuRepository lunchMenuRepository;


    public AdminRestController(RestaurantRepository restaurantRepository, UserService userService, MealRepository mealRepository, LunchMenuRepository lunchMenuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.lunchMenuRepository = lunchMenuRepository;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(
            @AuthenticationPrincipal User user
    ) {
        return restaurantRepository.findByAdministratorsContains(user);
    }


    @PostMapping(
            value = "/{restaurant}/meal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Meal> createMeal(
            @PathVariable("restaurant") Restaurant restaurant,
            @AuthenticationPrincipal User user,
            @RequestBody Meal meal
    ) {
        checkAccess(restaurant, user);
        meal.setRestaurant(restaurant);
        Meal created = mealRepository.save(meal);
        return ResponseEntity.created(getUri(created.getId(), REST_URL)).body(created);
    }


    @PostMapping(
            value = "/{restaurant}/lunchMenu",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LunchMenu> createLunchMenu(
            @PathVariable("restaurant") Restaurant restaurant,
            @AuthenticationPrincipal User user,
            @RequestBody(required = false) LunchMenu lunchMenu
    ) {
        checkAccess(restaurant, user);
        lunchMenu.setRestaurant(restaurant);
        LunchMenu created = lunchMenuRepository.save(lunchMenu);
        return ResponseEntity.created(getUri(created.getId(), REST_URL)).body(created);
    }

    @PutMapping(
            value = "/{restaurant}/lunchMenu/{lunchMenu}/{meal}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public LunchMenu addMealToLunchMenu(
            @PathVariable("restaurant") Restaurant restaurant,
            @AuthenticationPrincipal User user,
            @PathVariable("lunchMenu") LunchMenu lunchMenu,
            @PathVariable("meal") Meal meal
    ) {
        checkAccess(restaurant, user);
        if (lunchMenu.getMenuItems() == null) lunchMenu.setMenuItems(new HashSet<>());
        lunchMenu.getMenuItems().add(meal);
        return lunchMenu;
    }

    @DeleteMapping(
            value = "/{restaurant}/lunchMenu/{lunchMenu}/{meal}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeMealFromLunchMenu(
            @PathVariable("restaurant") Restaurant restaurant,
            @AuthenticationPrincipal User user,
            @PathVariable("lunchMenu") LunchMenu lunchMenu,
            @PathVariable("meal") Meal meal
    ) {
        checkAccess(restaurant, user);
        if (lunchMenu.getMenuItems() != null) {
            lunchMenu.getMenuItems().remove(meal);
        }
    }



}
