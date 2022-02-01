package donate.service;

import java.util.List;

import donate.exception.UserNotFoundException;
import donate.model.Donation;

public interface DonationService {

	Donation createDonation(Donation donation);
	void deleteDonation(Long id);
	List<Donation> getDonationFromUser(String name) throws UserNotFoundException;
	
}
