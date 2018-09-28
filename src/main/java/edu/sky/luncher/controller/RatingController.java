package edu.sky.luncher.controller;

import edu.sky.luncher.domain.Vote;
import edu.sky.luncher.dto.RestaurantWithLunchMenu;
import edu.sky.luncher.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private RatingService ratingService;


    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithLunchMenu> getAll() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<Vote> vote(

            @PathVariable Long id) {
        ratingService.vote();
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
