package edu.sky.luncher.repository;

import edu.sky.luncher.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}

