package donate.store.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.store.entity.PersonalizationDonateAlert;
import donate.store.entity.User;

@Repository
public interface PersonalizationDonateAlertRepository extends JpaRepository<PersonalizationDonateAlert, Long>{

	Set<PersonalizationDonateAlert> findByUser(User user);

	void deleteByPersonalizationIdAndUser(Long personalizationId, User user);

	Set<PersonalizationDonateAlert> findAllByUser(User user);

	Optional<PersonalizationDonateAlert> findByPersonalizationIdAndUser(Long widgetId, User user);

}
