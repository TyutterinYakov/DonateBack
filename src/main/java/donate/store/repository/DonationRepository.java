package donate.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import donate.store.entity.DonationEntity;
import donate.store.entity.UserEntity;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long>{

	List<DonationEntity> findAllByUser(UserEntity user);




	void deleteByDonateIdAndUser(Long id, UserEntity user);



	Optional<DonationEntity> findFirstByUserAndPlayOrderByDonateId(UserEntity user, boolean b);




	List<DonationEntity> findAllByUserOrderByDate(UserEntity user);

}
