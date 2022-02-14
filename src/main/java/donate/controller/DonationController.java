package donate.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.InvalidDataException;
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
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getAllDonationFromUser(Principal principal) throws UserNotFoundException, NullPointerException{
		List<Donation> donations = donationService.getDonationFromUser(principal.getName());
		return ResponseEntity.ok(donations);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteDonationById(@PathVariable Long id, Principal principal) throws UserNotFoundException, NullPointerException, InvalidDataException{
		if(id==null) {
			throw new NullPointerException();
		}
		donationService.deleteDonation(id, principal.getName());
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/alert/{userName}")
	public ResponseEntity<?> getAllDonationFromUser(@PathVariable("userName") String username) throws UserNotFoundException, NullPointerException{
		if(username.isEmpty()||username=="") {
			throw new NullPointerException();
		}
		return ResponseEntity.ok(donationService.getDonationFromUserAndPlay(username, false));
	}
	
	
	@ExceptionHandler
	public ResponseEntity<Void> userNotFoundException(UserNotFoundException ex){
		logger.error("",ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	@ExceptionHandler
	public ResponseEntity<Void> nullPointerException(NullPointerException ex){
		logger.error("",ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	@ExceptionHandler
	public ResponseEntity<Void> invalidDataException(InvalidDataException ex){
		logger.error("",ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
