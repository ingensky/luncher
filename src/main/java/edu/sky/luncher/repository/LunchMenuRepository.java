package edu.sky.luncher.repository;

import edu.sky.luncher.domain.LunchMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LunchMenuRepository extends JpaRepository<LunchMenu, Long> {

}
