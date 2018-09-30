package edu.sky.luncher.controller;

import edu.sky.luncher.domain.LunchMenu;
import edu.sky.luncher.domain.Meal;
import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.LunchMenuRepository;
import edu.sky.luncher.repository.MealRepository;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private RestaurantRepository restaurantRepository;
    private UserService userService;
    private MealRepository mealRepository;
    private LunchMenuRepository lunchMenuRepository;


    public AdminController(RestaurantRepository restaurantRepository, UserService userService, MealRepository mealRepository, LunchMenuRepository lunchMenuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.mealRepository = mealRepository;
        this.lunchMenuRepository = lunchMenuRepository;
    }

    @PostMapping(value = "/restaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant createRestaurant(
            @AuthenticationPrincipal User user,
            @RequestBody Restaurant restaurant
            ) {

        restaurant.getAdministrators().add(user);
        return restaurantRepository.save(restaurant);
    }

    @GetMapping(value = "/restaurant/{id}")
    public Restaurant getRestaurant(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) throws Exception {
       return restaurantRepository.findById(id).orElseThrow(Exception::new);
    }



    @PutMapping(value = "/restaurant/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant updateRestaurant(
            @AuthenticationPrincipal User user,
            @RequestBody Restaurant restaurant,
            @PathVariable Long id) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping(value = "/restaurant/{id}/meal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@PathVariable Long id, @RequestBody Meal meal) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        meal.setRestaurant(restaurant);
        return new ResponseEntity<>(mealRepository.save(meal), HttpStatus.CREATED);
    }

    @PutMapping(value = "/restaurant/{id}/meal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Meal updateMeal(@PathVariable Long id, @RequestBody Meal meal) {
        return mealRepository.save(meal);
    }


    @DeleteMapping(value = "/restaurant/{id_rest}/meal/{id_meal}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMeal(
            @PathVariable("id_rest") Long restaurantId,
            @PathVariable("id_meal") Long mealId
    ) {
//        mealRepository.delete(mealId);
    }

    @GetMapping(value = "/restaurant/**/meal/{id_meal}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> getMeal(
            @PathVariable("id_meal") Meal meal
    ) {
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }



    @GetMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(
            @AuthenticationPrincipal User user
    ) {
        return restaurantRepository.findByAdministratorsContains(user.getId());
    }


    @GetMapping(value = "/restaurant/**/lunch_meal/{id_lunch_menu}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LunchMenu> getLunchMenu(
            @PathVariable("id_lunch_menu") LunchMenu lunchMenu
    ) {
        return new ResponseEntity<>(lunchMenu, HttpStatus.OK);
    }

    @PostMapping(value = "/restaurant/**/lunch_meal", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LunchMenu> createLunchMenu(
            @RequestBody LunchMenu lunchMenu
    ) {
        return new ResponseEntity<>(lunchMenu, HttpStatus.CREATED);
    }

    @PutMapping(value = "/restaurant/**/lunch_meal/{id_lunch_menu}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LunchMenu> updateLunchMenu(
            @PathVariable("id_lunch_menu") LunchMenu lunchMenuFromDb,
            @RequestBody LunchMenu lunchMenu
    ) {
        BeanUtils.copyProperties(lunchMenu, lunchMenuFromDb, "id");

        return new ResponseEntity<>(lunchMenuRepository.save(lunchMenuFromDb), HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurant/**/lunch_meal/{id_lunch_menu}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLunchMenu(
            @PathVariable("id_lunch_menu") LunchMenu lunchMenu
    ) {
        lunchMenuRepository.delete(lunchMenu);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(
            @AuthenticationPrincipal User user
    ) {
        return userService.get(user.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(
            @RequestBody User user,
            @AuthenticationPrincipal User authUser) throws Exception {
        userService.update(user, authUser.getId());
    }



}
