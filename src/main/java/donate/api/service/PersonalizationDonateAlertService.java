package donate.api.service;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import donate.store.entity.WidgetEntity;

public interface PersonalizationDonateAlertService {
	
	WidgetEntity addPersonalization(WidgetEntity personalization, String string, MultipartFile file, MultipartFile music);
	void deletePersonalization(Long personalizationId, String string);
	WidgetEntity updatePersonalization(WidgetEntity personaliztion, String string, MultipartFile file, MultipartFile music);
	Set<WidgetEntity> getAllPersonalizationByUser(String name);
	WidgetEntity getWidgetByUserNameAndSumm(String userName, BigDecimal summ);
	WidgetEntity getPersonalizationByWidgetIdAndUser(Long widgetId, String name);

}
