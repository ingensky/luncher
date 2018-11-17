package edu.sky.luncher.service;

import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.domain.Vote;
import edu.sky.luncher.dto.RestaurantWithLunchMenu;
import edu.sky.luncher.repository.LunchMenuRepository;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.repository.VoteRepository;
import edu.sky.luncher.repository.VotingHistoryRepository;
import edu.sky.luncher.util.exception.VoteUnavailableException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RatingService {

    private static LocalTime updatePoint;
    private RestaurantRepository restaurantRepository;
    private VoteRepository voteRepository;

    public RatingService(RestaurantRepository restaurantRepository, LunchMenuRepository lunchMenuRepository,
                         VotingHistoryRepository votingHistoryRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }


    public List<RestaurantWithLunchMenu> getAll() {
        return restaurantRepository.getRestaurantWithLunchMenu(LocalDate.of(2018, 9, 30));
//        return restaurantRepository.getRestaurantWithLunchMenu(LocalDate.now());
    }

    public void vote(Restaurant restaurant, User user) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            Vote vote = new Vote(LocalDate.now(), user, restaurant);
            voteRepository.save(vote);
        } else throw new VoteUnavailableException("You can't vote after 11:00 AM");
    }

    @Scheduled(cron = "${cron.expression}")
    @Transactional
    public void updateHistory() {
        updatePoint = LocalTime.now();
        System.out.println("Sceduled thing trying to start");
    }

}
