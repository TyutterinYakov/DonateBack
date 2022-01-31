package donate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
