package donate.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.store.entity.User;
import donate.store.entity.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long>{

	List<Withdraw> findAllByUser(User user);

	List<Withdraw> findAllByUserOrderByDateWithdraw(User userByUsername);

}
