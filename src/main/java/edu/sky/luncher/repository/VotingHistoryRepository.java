package edu.sky.luncher.repository;

import edu.sky.luncher.domain.VotingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingHistoryRepository extends JpaRepository<VotingHistory, Long> {


}
