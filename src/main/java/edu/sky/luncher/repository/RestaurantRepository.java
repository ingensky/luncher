package edu.sky.luncher.repository;

import edu.sky.luncher.domain.Restaurant;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.domain.dto.RestaurantWithLunchMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    Restaurant findByName(String name);

    Restaurant findByAdministratorsContains(User user);

    Optional<Restaurant> findById(Long id);

    Restaurant getById(Long id);



    @Query("SELECT DISTINCT NEW edu.sky.luncher.domain.dto.RestaurantWithLunchMenu(r.id, r.name, lm, COUNT(v)) " +
            "FROM Restaurant r " +
            "INNER JOIN LunchMenu lm ON lm.restaurant.id = r.id AND lm.date = :date " +
            "LEFT OUTER JOIN Vote v ON v.restaurant.id = r.id AND v.date = :date " +
            "GROUP BY r.id ORDER BY 4 DESC")
    List<RestaurantWithLunchMenu> getRestaurantWithLunchMenu(@Param("date") LocalDate date);

}
