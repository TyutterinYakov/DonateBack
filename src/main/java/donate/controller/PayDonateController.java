package donate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.UserNotFoundException;
import donate.model.DonationRequest;
import donate.service.DonationService;

@RestController
@RequestMapping("/pay")
@CrossOrigin("*")
public class PayDonateController {
	private static final Logger logger = LoggerFactory.getLogger(PayDonateController.class);

	private DonationService donationService;
	
	@Autowired
	public PayDonateController(DonationService donationService) {
		super();
		this.donationService = donationService;
	}




	@PostMapping("/")
	public ResponseEntity<?> payDonate(@RequestBody DonationRequest request) {
		
		try {
			donationService.createDonation(request);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
			logger.error(request.getUsername()+" -------", e);
			return new ResponseEntity<>("Неверно имя пользователя",HttpStatus.BAD_REQUEST);
		}
	}
}
