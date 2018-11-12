package edu.sky.luncher.controller;

import edu.sky.luncher.domain.LunchMenu;
import edu.sky.luncher.domain.Meal;
import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.LunchMenuRepository;
import edu.sky.luncher.repository.MealRepository;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static edu.sky.luncher.util.Util.checkAccess;
import static edu.sky.luncher.util.Util.getUri;

@RestController
@RequestMapping(AdminRestController.REST_URL)
@PreAuthorize("hasRole('ADMIN')")
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
        Meal created = mealRepository.save(meal);
        return ResponseEntity.created(getUri(created.getId(), REST_URL)).body(created);
    }


    @PostMapping(
            value = "/{restaurant}/meal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LunchMenu> createLunchMenu(
            @PathVariable("restaurant") Restaurant restaurant,
            @AuthenticationPrincipal User user,
            @RequestBody LunchMenu lunchMenu
    ) {
        checkAccess(restaurant, user);
        LunchMenu created = lunchMenuRepository.save(lunchMenu);
        return ResponseEntity.created(getUri(created.getId(), REST_URL)).body(created);
    }

}
