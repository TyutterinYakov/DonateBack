package donate.api.service;

import java.util.List;

import donate.api.exception.UserNotFoundException;
import donate.api.model.DonationRequest;
import donate.store.entity.DonationEntity;

public interface DonationService {

	void createDonation(DonationRequest request);
	void deleteDonation(Long id, String username);
	List<DonationEntity> getDonationFromUser(String name);
	DonationEntity getDonationFromUserAndPlay(String username, boolean b);
	
}
