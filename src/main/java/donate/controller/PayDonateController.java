package donate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.InvalidDataException;
import donate.exception.UserNotFoundException;
import donate.model.DonationRequest;
import donate.service.DonationService;
import donate.service.UserService;

@RestController
@RequestMapping("/pay")
@CrossOrigin("*")
public class PayDonateController {
	private static final Logger logger = LoggerFactory.getLogger(PayDonateController.class);

	private DonationService donationService;
	private UserService userService;
	
	@Autowired
	public PayDonateController(DonationService donationService, UserService userService) {
		super();
		this.donationService = donationService;
		this.userService = userService;
	}



	@PostMapping("/")
	public ResponseEntity<?> payDonate(@RequestBody DonationRequest request) {
		
		try {
			donationService.createDonation(request);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
			logger.error(request.getUsername(), e);
			return new ResponseEntity<>("Неверно имя пользователя",HttpStatus.BAD_REQUEST);
		} catch (InvalidDataException e) {
			logger.error(request.getSumm().toString(), e);
			return new ResponseEntity<>("Слишком маленькая сумма",HttpStatus.BAD_REQUEST);
		}
	}
	

	@GetMapping("/{userName}")
	public ResponseEntity<?> getMinSummDonate(@PathVariable String userName){
		try {
			if(userName.isEmpty()&&userName.strip()=="") {
				throw new NullPointerException();
		}
			return ResponseEntity.ok(userService.getMinSummDonateFromUserName(userName));
		} catch(NullPointerException ex) {
			logger.error("getMinSummDonate пустое имя", ex);
			return new ResponseEntity<>("Нет данных об имени", HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			logger.error(userName, e);
			return new ResponseEntity<>("Нет такого пользователя", HttpStatus.BAD_REQUEST);
		} 
	}
}
