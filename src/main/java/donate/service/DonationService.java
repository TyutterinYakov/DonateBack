package donate.service;

import java.util.List;

import donate.exception.InvalidDataException;
import donate.exception.UserNotFoundException;
import donate.model.Donation;
import donate.model.DonationRequest;

public interface DonationService {

	void createDonation(DonationRequest request) throws UserNotFoundException, InvalidDataException;
	void deleteDonation(Long id, String username) throws UserNotFoundException, InvalidDataException;
	List<Donation> getDonationFromUser(String name) throws UserNotFoundException;
	Donation getDonationFromUserAndPlay(String username, boolean b) throws UserNotFoundException;
	
}
