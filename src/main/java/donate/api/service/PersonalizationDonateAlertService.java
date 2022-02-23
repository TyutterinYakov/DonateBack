package donate.api.service;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import donate.store.entity.PersonalizationDonateAlert;

public interface PersonalizationDonateAlertService {
	
	PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization, String string, MultipartFile file, MultipartFile music);
	void deletePersonalization(Long personalizationId, String string);
	PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personaliztion, String string, MultipartFile file, MultipartFile music);
	Set<PersonalizationDonateAlert> getAllPersonalizationByUser(String name);
	PersonalizationDonateAlert getWidgetByUserNameAndSumm(String userName, BigDecimal summ);
	PersonalizationDonateAlert getPersonalizationByWidgetIdAndUser(Long widgetId, String name);

}
