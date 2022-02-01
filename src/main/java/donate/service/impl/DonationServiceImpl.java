package donate.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.exception.UserNotFoundException;
import donate.model.Donation;
import donate.model.DonationRequest;
import donate.model.User;
import donate.repository.DonationRepository;
import donate.repository.UserRepository;
import donate.service.DonationService;

@Service
public class DonationServiceImpl implements DonationService{

	private static final Logger logger = LoggerFactory.getLogger(DonationServiceImpl.class);
	
	private DonationRepository donationDao;
	private UserRepository userDao;
	
	@Autowired
	public DonationServiceImpl(DonationRepository donationDao, UserRepository userDao) {
		super();
		this.userDao = userDao;
		this.donationDao = donationDao;
	}

	
	public void createDonation(DonationRequest request) throws UserNotFoundException {
		User user = userDao.findByUserName(request.getUsername()).orElseThrow(()->
				new UserNotFoundException());
		Donation donation = new Donation();
		donation.setSumm(request.getSumm());
		donation.setDate(LocalDateTime.now());
		donation.setDonationName(request.getDonateName());
		donation.setMessage(request.getMessage());
		donation.setUser(user);
		donationDao.save(donation);
		
	}

	public void deleteDonation(Long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Donation> getDonationFromUser(String name) throws UserNotFoundException {
		User user = userDao.findByUserName(name).orElseThrow(()->
				new UserNotFoundException());
		return donationDao.findAllByUser(user);
	}

	
}
