package edu.sky.luncher.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.util.Views;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        System.out.println(user);
      return  userService.addUser(user);
    }

    @GetMapping
    @JsonView(Views.Name.class)
    public List<User> list() {
        return userService.findAll();
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createAdmin(
            @RequestBody User user,
            @RequestBody(required = false) Restaurant restaurant,
            @RequestParam(required = false) String restaurantName) {
        Restaurant restaurantFromDb = null;
        if (restaurantName != null) {
            restaurantFromDb = restaurantRepository.findByName(restaurantName);
        } else if (restaurant != null) {
            restaurantFromDb = restaurant;
        }
        if (restaurantFromDb != null) {
            restaurantFromDb.getAdministrators().add(user);
        }
        restaurantRepository.save(restaurantFromDb);
        return userService.addAdmin(user);

    }
}
