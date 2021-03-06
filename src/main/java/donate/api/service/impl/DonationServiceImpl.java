package donate.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.api.exception.BadRequestException;
import donate.api.exception.UserNotFoundException;
import donate.api.model.DonationRequest;
import donate.api.service.DonationService;
import donate.store.entity.DonationEntity;
import donate.store.entity.UserEntity;
import donate.store.repository.DonationRepository;
import donate.store.repository.UserRepository;

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

	
	public void createDonation(DonationRequest request) {
		UserEntity user = userDao.findByUserName(request.getUsername()).orElseThrow(()->
				new UserNotFoundException());
		DonationEntity donation = new DonationEntity();
		if((user.getMinSummDonate()).compareTo(request.getSumm())==1) {
			throw new BadRequestException();
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
	public void deleteDonation(Long id, String userName) {
		UserEntity user = userDao.findByUserName(userName).orElseThrow(()->
				new UserNotFoundException());
		DonationEntity donation = donationDao.findById(id).orElseThrow(()->
			new BadRequestException()
		);
		BigDecimal allMoneyTime = user.getAllTimeMoney().subtract(donation.getSumm());
		Long countMessage = user.getCountMessage();
		user.setCountMessage(--countMessage);
		user.setAllTimeMoney(allMoneyTime);
		donationDao.deleteByDonateIdAndUser(id, user);
	}


	@Override
	public List<DonationEntity> getDonationFromUser(String name) {
		UserEntity user = userDao.findByUserName(name).orElseThrow(()->
				new UserNotFoundException());
		return donationDao.findAllByUserOrderByDate(user);
	}


	@Override
	public DonationEntity getDonationFromUserAndPlay(String username, boolean b) {
		DonationEntity d = new DonationEntity();
		System.out.println(username);
		UserEntity user = userDao.findByUserName(username).orElseThrow(()->
			new UserNotFoundException());
		Optional<DonationEntity> donation = donationDao.findFirstByUserAndPlayOrderByDonateId(user, b);
		if(donation.isPresent()) {
			d = donation.get();
			d.setPlay(true);
			return donationDao.save(d);
		}
		d.setSumm(new BigDecimal(0));
		
		return d;
	}

	
}
