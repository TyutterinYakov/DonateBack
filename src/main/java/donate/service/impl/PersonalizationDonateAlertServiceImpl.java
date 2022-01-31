package donate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.model.PersonalizationDonateAlert;
import donate.repository.PersonalizationDonateAlertRepository;
import donate.service.PersonalizationDonateAlertService;

@Service
public class PersonalizationDonateAlertServiceImpl implements PersonalizationDonateAlertService {

	private PersonalizationDonateAlertRepository personalizationDao;
	
	@Autowired
	public PersonalizationDonateAlertServiceImpl(PersonalizationDonateAlertRepository personalizationDao) {
		super();
		this.personalizationDao = personalizationDao;
	}

	
	public PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletePersonalization(Long personalizationId) {
		// TODO Auto-generated method stub
		
	}

	public PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personaliztion) {
		// TODO Auto-generated method stub
		return null;
	}

}
