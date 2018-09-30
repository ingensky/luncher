package edu.sky.luncher.repository;

import edu.sky.luncher.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {

//    void delete(Long mealId);
}
