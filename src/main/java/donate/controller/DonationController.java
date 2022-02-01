package donate.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.UserNotFoundException;
import donate.model.Donation;
import donate.service.DonationService;

@RestController
@RequestMapping("/donation")
@CrossOrigin("*")
public class DonationController {

	private static final Logger logger = LoggerFactory.getLogger(DonationController.class);
	private DonationService donationService;
	
	@Autowired
	public DonationController(DonationService donationService) {
		super();
		this.donationService = donationService;
	}




	@GetMapping("/")
	public ResponseEntity<?> getAllDonationFromUser(Principal principal){
		
		try {
			List<Donation> donations = donationService.getDonationFromUser(principal.getName());
			logger.info(donations.toString());
			return ResponseEntity.ok(donations);
		} catch (UserNotFoundException e) {
			logger.error(principal.getName());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NullPointerException ex) {
			logger.error("Сообщения невозможно получить без имени");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
