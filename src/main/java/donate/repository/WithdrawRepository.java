package donate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.model.User;
import donate.model.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long>{

	List<Withdraw> findAllByUser(User user);

	List<Withdraw> findAllByUserOrderByDateWithdraw(User userByUsername);

}
