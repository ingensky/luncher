package edu.sky.luncher.repository;

import edu.sky.luncher.domain.LunchMenu;
import edu.sky.luncher.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LunchMenuRepository extends JpaRepository<LunchMenu, Long> {

    LunchMenu findByDateAndRestaurant(LocalDate date, Restaurant restaurant);
}
