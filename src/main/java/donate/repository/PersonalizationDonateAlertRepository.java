package donate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.model.PersonalizationDonateAlert;
import donate.model.User;

@Repository
public interface PersonalizationDonateAlertRepository extends JpaRepository<PersonalizationDonateAlert, Long>{

	List<PersonalizationDonateAlert> findByUser(User user);

	void deleteByPersonalizationIdAndUser(Long personalizationId, User user);

}
