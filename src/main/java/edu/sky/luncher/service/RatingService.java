package edu.sky.luncher.service;

import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.domain.Vote;
import edu.sky.luncher.domain.VotingHistory;
import edu.sky.luncher.domain.dto.RestaurantWithLunchMenu;
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

    private RestaurantRepository restaurantRepository;
    private VotingHistoryRepository votingHistoryRepository;
    private VoteRepository voteRepository;

    public RatingService(RestaurantRepository restaurantRepository, LunchMenuRepository lunchMenuRepository,
                         VotingHistoryRepository votingHistoryRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.votingHistoryRepository = votingHistoryRepository;
        this.voteRepository = voteRepository;
    }


    public List<RestaurantWithLunchMenu> getAllForToday() {
        System.out.println();
        return getAll(LocalDate.now());
    }

    public List<RestaurantWithLunchMenu> getAll(LocalDate date) {
        return restaurantRepository.getRestaurantWithLunchMenu(date);
    }


    public void vote(Restaurant restaurant, User user) {
        if (LocalTime.now().isBefore(LocalTime.of(23, 0))) {
            LocalDate date = LocalDate.now();
            Vote vote = new Vote(date, user, restaurant);
            Vote byDateAndUser = voteRepository.findByDateAndUser(date, user);
            if (byDateAndUser != null) {
                vote.setId(byDateAndUser.getId());
            }
            voteRepository.save(vote);
        }
//        else throw new VoteUnavailableException("You can't vote after 11:00 AM");
    }

    @Scheduled(cron = "${cron.expression}")
    @Transactional
    public void updateHistory() {
        getAllForToday().forEach(restaurantWithLunchMenu ->
                votingHistoryRepository.save(new VotingHistory(
                        LocalDate.now(),
                        restaurantRepository.getById(restaurantWithLunchMenu.getRestaurantId()),
                        (int) restaurantWithLunchMenu.getRating())));
    }


}
