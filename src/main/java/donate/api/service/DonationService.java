package donate.api.service;

import java.util.List;

import donate.api.exception.UserNotFoundException;
import donate.api.model.DonationRequest;
import donate.store.entity.Donation;

public interface DonationService {

	void createDonation(DonationRequest request);
	void deleteDonation(Long id, String username);
	List<Donation> getDonationFromUser(String name);
	Donation getDonationFromUserAndPlay(String username, boolean b);
	
}
