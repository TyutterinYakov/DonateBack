package donate.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import donate.exception.UserNotFoundException;
import donate.model.Donation;
import donate.model.PersonalizationDonateAlert;

public interface PersonalizationDonateAlertService {
	
	PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization, String string, MultipartFile file) throws UserNotFoundException, IOException;
	void deletePersonalization(Long personalizationId, String string) throws UserNotFoundException;
	PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personaliztion, String string, MultipartFile file) throws UserNotFoundException;;
	List<PersonalizationDonateAlert> getAllPersonalizationByUser(String name) throws UserNotFoundException;
	PersonalizationDonateAlert getWidgetByUserNameAndSumm(String userName, BigDecimal summ) throws UserNotFoundException;
	PersonalizationDonateAlert getPersonalizationByWidgetIdAndUser(Long widgetId, String name) throws UserNotFoundException;

}
