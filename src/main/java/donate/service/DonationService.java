package donate.service;

import donate.model.Donation;

public interface DonationService {

	Donation createDonation(Donation donation);
	void deleteDonation(Long id);
	
}
