package donate.service;

import java.util.List;

import donate.exception.UserNotFoundException;
import donate.model.Donation;
import donate.model.DonationRequest;

public interface DonationService {

	void createDonation(DonationRequest request) throws UserNotFoundException;
	void deleteDonation(Long id, String username) throws UserNotFoundException;
	List<Donation> getDonationFromUser(String name) throws UserNotFoundException;
	
}
