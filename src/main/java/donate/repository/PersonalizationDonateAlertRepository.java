package donate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.model.PersonalizationDonateAlert;

@Repository
public interface PersonalizationDonateAlertRepository extends JpaRepository<PersonalizationDonateAlert, Long>{

}
