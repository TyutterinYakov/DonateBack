package donate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import donate.model.Donation;
import donate.model.User;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long>{

	List<Donation> findAllByUser(User user);




	void deleteByDonateIdAndUser(Long id, User user);



	Optional<Donation> findFirstByUserAndPlayOrderByDonateId(User user, boolean b);

}
