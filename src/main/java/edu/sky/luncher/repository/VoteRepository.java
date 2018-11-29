package edu.sky.luncher.repository;

import edu.sky.luncher.domain.User;
import edu.sky.luncher.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByDateAndUser(LocalDate date, User user);

}

