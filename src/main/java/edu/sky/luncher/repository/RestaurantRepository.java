package edu.sky.luncher.repository;

import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.dto.RestaurantWithLunchMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

    Restaurant findByAdministratorsContains(Long id);

    Optional<Restaurant> findById(Long id);



    @Query("SELECT DISTINCT NEW edu.sky.luncher.dto.RestaurantWithLunchMenu(r.id, r.name, lm, vh.votes + COUNT(v)) FROM Restaurant r" +
            " INNER JOIN LunchMenu lm ON lm.restaurant.id = r.id AND lm.date = :date" +
            " INNER JOIN VotingHistory vh ON vh.restaurant.id = r.id" +
            " INNER JOIN Vote v ON v.restaurant.id = r.id GROUP BY r.id ORDER BY 4 DESC ")
    List<RestaurantWithLunchMenu> retrieveRestaurantsWithLunchMenu(@Param("date") LocalDate date);


}
