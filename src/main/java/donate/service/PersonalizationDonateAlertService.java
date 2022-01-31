package donate.service;

import donate.model.PersonalizationDonateAlert;

public interface PersonalizationDonateAlertService {
	
	PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization);
	void deletePersonalization(Long personalizationId);
	PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personaliztion);

}
