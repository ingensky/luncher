package edu.sky.luncher.service;

import edu.sky.luncher.dto.RestaurantWithLunchMenu;
import edu.sky.luncher.repository.LunchMenuRepository;
import edu.sky.luncher.repository.RestaurantRepository;
import edu.sky.luncher.repository.VoteRepository;
import edu.sky.luncher.repository.VotingHistoryRepository;
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
    private LunchMenuRepository lunchMenuRepository;
    private VotingHistoryRepository votingHistoryRepository;
    private VoteRepository voteRepository;

    public RatingService(RestaurantRepository restaurantRepository, LunchMenuRepository lunchMenuRepository,
                         VotingHistoryRepository votingHistoryRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.lunchMenuRepository = lunchMenuRepository;
        this.votingHistoryRepository = votingHistoryRepository;
        this.voteRepository = voteRepository;
    }



    public List<RestaurantWithLunchMenu> getAll() {
        return restaurantRepository.retrieveRestaurantsWithLunchMenu(LocalDate.now());
    }

    public void vote() {

    }

    @Scheduled(cron = "${cron.expression}")
    @Transactional
    public void updateHistory() {
        updatePoint = LocalTime.now();

        System.out.println("Sceduled thing trying to start");
    }

}
