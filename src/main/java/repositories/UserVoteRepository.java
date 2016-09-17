package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.UserVote;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, Integer> {

}
