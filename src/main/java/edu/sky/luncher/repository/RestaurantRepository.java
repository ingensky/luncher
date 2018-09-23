package edu.sky.luncher.repository;

import edu.sky.luncher.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

    Restaurant findByAdministratorsContains(Long id);

    Optional<Restaurant> findById(Long id);


}
