package donate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import donate.model.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long>{

}
