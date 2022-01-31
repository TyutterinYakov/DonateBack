package donate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.model.Donation;
import donate.repository.DonationRepository;
import donate.service.DonationService;

@Service
public class DonationServiceImpl implements DonationService{

	private static final Logger logger = LoggerFactory.getLogger(DonationServiceImpl.class);
	
	private DonationRepository donationDao;
	
	@Autowired
	public DonationServiceImpl(DonationRepository donationDao) {
		super();
		this.donationDao = donationDao;
	}

	
	public Donation createDonation(Donation donation) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteDonation(Long id) {
		// TODO Auto-generated method stub
		
	}

	
}
