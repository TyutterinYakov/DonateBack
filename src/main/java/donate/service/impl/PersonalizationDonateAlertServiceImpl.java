package donate.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.exception.UserNotFoundException;
import donate.model.PersonalizationDonateAlert;
import donate.model.User;
import donate.repository.PersonalizationDonateAlertRepository;
import donate.repository.UserRepository;
import donate.service.PersonalizationDonateAlertService;

@Service
public class PersonalizationDonateAlertServiceImpl implements PersonalizationDonateAlertService {

	private static final Logger logger = LoggerFactory.getLogger(PersonalizationDonateAlertServiceImpl.class);
	
	private PersonalizationDonateAlertRepository personalizationDao;
	private UserRepository userDao;
	
	@Autowired
	public PersonalizationDonateAlertServiceImpl(PersonalizationDonateAlertRepository personalizationDao,
			UserRepository userDao) {
		super();
		this.personalizationDao = personalizationDao;
		this.userDao = userDao;
	}

	
	public PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization, String name) throws UserNotFoundException {
		User user = userDao.findByUserName(name).orElseThrow(()->
				new UserNotFoundException());
		personalization.setUser(user);
		return personalizationDao.save(personalization);
	}


	@Transactional
	public void deletePersonalization(Long personalizationId, String userName) throws UserNotFoundException{
		User user = userDao.findByUserName(userName).orElseThrow(()->
		new UserNotFoundException());
		personalizationDao.deleteByPersonalizationIdAndUser(personalizationId, user);
	}

	public PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personaliztion) {
		// TODO Auto-generated method 
		return null;
	}


	@Override
	public List<PersonalizationDonateAlert> getAllPersonalizationByUser(String name) throws UserNotFoundException {
		User user = userDao.findByUserName(name).orElseThrow(()->
				new UserNotFoundException());
		return personalizationDao.findByUser(user);
	}


	@Override
	public PersonalizationDonateAlert getWidgetByUserNameAndSumm(String userName, BigDecimal summ) throws UserNotFoundException {
		
		User user = userDao.findByUserName(userName).orElseThrow(()->
			new UserNotFoundException()
		);
		List<PersonalizationDonateAlert> personalization = personalizationDao.findAllByUser(user);
		if(personalization.size()>0) {
			PersonalizationDonateAlert widget = new PersonalizationDonateAlert();
			widget.setSummMin(new BigDecimal(0));
			for(PersonalizationDonateAlert pr: personalization) {
				if(pr.getSummMin().floatValue()<summ.floatValue()) {
					if(widget.getSummMin().floatValue()<pr.getSummMin().floatValue()) {
					widget=pr;
					}
				}
			}
			return widget;
		}
		return null;
	}

}
