package donate.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.store.entity.UserEntity;
import donate.store.entity.WithdrawEntity;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long>{

	List<WithdrawEntity> findAllByUser(UserEntity user);

	List<WithdrawEntity> findAllByUserOrderByDateWithdraw(UserEntity userByUsername);

}
