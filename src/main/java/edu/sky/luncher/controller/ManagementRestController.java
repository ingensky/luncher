package edu.sky.luncher.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.Role;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.repository.UserRepository;
import edu.sky.luncher.util.Views;
import edu.sky.luncher.util.exception.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.sky.luncher.util.Util.getUri;

@RestController
@RequestMapping(ManagementRestController.REST_URL)
//@PreAuthorize("hasRole('ROLE_MANAGER')")
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
    @JsonView(Views.Body.class)
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.save(restaurant);
        return ResponseEntity.created(getUri(created.getId(), REST_URL)).body(created);
    }

    @PutMapping(value = "/{restaurant}/admin", produces =MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public User addAdmin(
            @RequestBody User user,
            @PathVariable("restaurant") Restaurant restaurant
    ) {
        User createdUser = userRepository.save(user);
        createdUser.setRoles(Stream.of(Role.ROLE_USER, Role.ROLE_ADMIN)
                .collect(Collectors.toCollection(HashSet::new)));
        restaurant.getAdministrators().add(createdUser);
        return createdUser;
    }

    @DeleteMapping(value = "/{restaurant}/admin/{user}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void removeAdmin(
            @PathVariable("restaurant") @NotNull Restaurant restaurant,
            @PathVariable("user") @NotNull User user
    ) {
        if (restaurant.getAdministrators().contains(user)) {
            restaurant.getAdministrators().remove(user);
        } else throw new IllegalRequestDataException("This administrator doesn't belong to this restaurant.");
    }


}
