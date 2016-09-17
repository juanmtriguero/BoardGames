package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Following;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Integer> {

}
