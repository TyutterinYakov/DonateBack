package donate.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;

import donate.exception.UserNotFoundException;
import donate.model.Donation;
import donate.model.PersonalizationDonateAlert;

public interface PersonalizationDonateAlertService {
	
	PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization, String string) throws UserNotFoundException;
	void deletePersonalization(Long personalizationId, String string) throws UserNotFoundException;
	PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personaliztion);
	List<PersonalizationDonateAlert> getAllPersonalizationByUser(String name) throws UserNotFoundException;
	PersonalizationDonateAlert getWidgetByUserNameAndSumm(String userName, BigDecimal summ) throws UserNotFoundException;

}
