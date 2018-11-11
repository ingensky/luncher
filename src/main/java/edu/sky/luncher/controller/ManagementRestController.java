package edu.sky.luncher.controller;

import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.Role;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(ManagementRestController.REST_URL)
public class ManagementRestController {
    static final String REST_URL = "/restaurants";


    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    @Autowired
    public ManagementRestController(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurant_id}/admin")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void addAdmin(
            @RequestBody User user,
            @PathVariable("restaurant_id") Restaurant restaurant
    ) {
        User createdUser = userRepository.save(user);
        createdUser.setRoles(Stream.of(Role.USER, Role.ADMIN)
                .collect(Collectors.toCollection(HashSet::new)));
        restaurant.getAdministrators().add(createdUser);
    }

    @PutMapping(value = "/{restaurant_id}/admin/{user_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void addAdmin(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @PathVariable("user_id") User user
    ) {
        restaurant.getAdministrators().remove(user);
    }


}
