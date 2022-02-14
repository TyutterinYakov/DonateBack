package donate.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import donate.exception.InvalidDataException;
import donate.exception.UserNotFoundException;
import donate.model.PersonalizationDonateAlert;
import donate.model.User;
import donate.repository.PersonalizationDonateAlertRepository;
import donate.repository.UserRepository;
import donate.service.PersonalizationDonateAlertService;
import donate.util.UploadAndRemoveImage;

@Service
public class PersonalizationDonateAlertServiceImpl implements PersonalizationDonateAlertService {

	private static final Logger logger = LoggerFactory.getLogger(PersonalizationDonateAlertServiceImpl.class);
	
	private PersonalizationDonateAlertRepository personalizationDao;
	private UserRepository userDao;
	private UploadAndRemoveImage imageUtil;
	
	@Autowired
	public PersonalizationDonateAlertServiceImpl(PersonalizationDonateAlertRepository personalizationDao,
			UserRepository userDao, UploadAndRemoveImage imageUtil) {
		super();
		this.personalizationDao = personalizationDao;
		this.userDao = userDao;
		this.imageUtil = imageUtil;
	}


	
	public PersonalizationDonateAlert addPersonalization(PersonalizationDonateAlert personalization, String name, MultipartFile file, MultipartFile music) throws UserNotFoundException, IOException {
		User user = getUserByUsername(name);
		personalization.setUser(user);
		personalization.setImage(imageUtil.uploadImage(file, "widget"));
		personalization.setMusic(imageUtil.uploadImage(music, "widget/music"));
		return personalizationDao.save(personalization);
	}


	@Transactional
	public void deletePersonalization(Long personalizationId, String userName) throws UserNotFoundException{
		User user = getUserByUsername(userName);
		Optional<PersonalizationDonateAlert> widget =  personalizationDao.findByPersonalizationIdAndUser(personalizationId, user);
		if(widget.isPresent()) {
			imageUtil.deleteImage(widget.get().getImage(), "widget/");
			imageUtil.deleteImage(widget.get().getMusic(), "widget/music/");
			personalizationDao.delete(widget.get());
		}
	}



	@Override
	public List<PersonalizationDonateAlert> getAllPersonalizationByUser(String name) throws UserNotFoundException {
		User user = getUserByUsername(name);
		return personalizationDao.findByUser(user);
	}


	@Override
	public PersonalizationDonateAlert getWidgetByUserNameAndSumm(String userName, BigDecimal summ) throws UserNotFoundException {
		
		User user = getUserByUsername(userName);
		List<PersonalizationDonateAlert> personalization = personalizationDao.findAllByUser(user);
		if(personalization.size()>0) {
			PersonalizationDonateAlert widget = new PersonalizationDonateAlert();
			widget.setSummMin(new BigDecimal(0));
			for(PersonalizationDonateAlert pr: personalization) {
				if(pr.getSummMin().floatValue()<=summ.floatValue()) {
					if(widget.getSummMin().floatValue()<pr.getSummMin().floatValue()) {
					widget=pr;
					}
				}
			}
			return widget;
		}
		return new PersonalizationDonateAlert();
	}


	@Override
	public PersonalizationDonateAlert updatePersonalization(PersonalizationDonateAlert personalization, String userName, MultipartFile file, MultipartFile music)
			throws UserNotFoundException, InvalidDataException {
		User user = getUserByUsername(userName);
		PersonalizationDonateAlert widget = personalizationDao.findById(personalization.getPersonalizationId()).orElseThrow(InvalidDataException::new);
		if(widget.getUser().equals(user)) {
			personalization.setImage(widget.getImage());
			personalization.setMusic(widget.getMusic());
			personalization.setUser(user);
			if(file!=null) {
				imageUtil.deleteImage(widget.getImage(), "widget/");
				try {
					String imageName = imageUtil.uploadImage(file, "widget");
					personalization.setImage(imageName);
				} catch (IOException e) { 				
					logger.error("", e);
					throw new IllegalStateException();
				}
			}
			if(music!=null) {
				imageUtil.deleteImage(widget.getMusic(), "widget/music");
				try {
					String musicName = imageUtil.uploadImage(music, "widget/music");
					personalization.setMusic(musicName);
				} catch (IOException e) { 				
					logger.error("", e);
					throw new IllegalStateException();
				}
			}
			return personalizationDao.save(personalization);
		}
		return null;
	}


	@Override
	public PersonalizationDonateAlert getPersonalizationByWidgetIdAndUser(Long widgetId, String userName)
			throws UserNotFoundException {
		User user = getUserByUsername(userName);
		return personalizationDao.findByPersonalizationIdAndUser(widgetId, user).get(); //TODO
	}
	
	
	
	
	private User getUserByUsername(String userName) throws UserNotFoundException {
		return userDao.findByUserName(userName).orElseThrow(()->
		new UserNotFoundException()
	);
	}

}
