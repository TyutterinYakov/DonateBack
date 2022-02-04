package donate.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.exception.InvalidDataException;
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

	
	public void createDonation(DonationRequest request) throws UserNotFoundException, InvalidDataException {
		User user = userDao.findByUserName(request.getUsername()).orElseThrow(()->
				new UserNotFoundException());
		Donation donation = new Donation();
		if((user.getMinSummDonate()).compareTo(request.getSumm())==1) {
			throw new InvalidDataException();
		}
		donation.setSumm(request.getSumm());
		donation.setDate(LocalDateTime.now());
		donation.setDonationName(request.getDonateName());
		donation.setMessage(request.getMessage());
		BigDecimal balance = user.getBalance().add(request.getSumm());
		BigDecimal allMoneyTime = user.getAllTimeMoney().add(request.getSumm());
		Long countMessage = user.getCountMessage();
		user.setCountMessage(++countMessage);
		user.setAllTimeMoney(allMoneyTime);
		user.setBalance(balance);
		donation.setUser(user);
		donationDao.save(donation);
		
	}

	@Transactional
	public void deleteDonation(Long id, String userName) throws UserNotFoundException, InvalidDataException {
		User user = userDao.findByUserName(userName).orElseThrow(()->
				new UserNotFoundException());
		Donation donation = donationDao.findById(id).orElseThrow(()->
			new InvalidDataException()
		);
		BigDecimal allMoneyTime = user.getAllTimeMoney().subtract(donation.getSumm());
		Long countMessage = user.getCountMessage();
		user.setCountMessage(--countMessage);
		user.setAllTimeMoney(allMoneyTime);
		donationDao.deleteByDonateIdAndUser(id, user);
	}


	@Override
	public List<Donation> getDonationFromUser(String name) throws UserNotFoundException {
		User user = userDao.findByUserName(name).orElseThrow(()->
				new UserNotFoundException());
		return donationDao.findAllByUserOrderByDate(user);
	}


	@Override
	public Donation getDonationFromUserAndPlay(String username, boolean b) throws UserNotFoundException {
		Donation d = new Donation();
		System.out.println(username);
		User user = userDao.findByUserName(username).orElseThrow(()->
		new UserNotFoundException());
		Optional<Donation> donation = donationDao.findFirstByUserAndPlayOrderByDonateId(user, b);
		if(donation.isPresent()) {
			d = donation.get();
			d.setPlay(true);
			return donationDao.save(d);
		}
		d.setSumm(new BigDecimal(0));
		
		return d;
	}

	
}
