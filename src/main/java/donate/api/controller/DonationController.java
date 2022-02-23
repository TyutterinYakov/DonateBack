package donate.api.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.api.service.DonationService;
import donate.store.entity.DonationEntity;

@RestController
@RequestMapping
@CrossOrigin("*")
public class DonationController {

	private static final Logger logger = LoggerFactory.getLogger(DonationController.class);
	private DonationService donationService;
	
	@Autowired
	public DonationController(DonationService donationService) {
		super();
		this.donationService = donationService;
	}

	
	//USER
	public static final String GET_ALL_DONATION_BY_USER_PRINCIPAL = "/api/user/donation";
	public static final String DELETE_DONATION_USER_BY_ID = "/api/user/donation/{donationId}";
	//ANY
	public static final String GET_ALL_DONATION_USER_DONT_PLAY = "/api/user/donation/{userName}";


	//USER

	@GetMapping(GET_ALL_DONATION_BY_USER_PRINCIPAL)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getAllDonationFromUser(Principal principal){
		List<DonationEntity> donations = donationService.getDonationFromUser(principal.getName());
		return ResponseEntity.ok(donations);
	}
	
	@DeleteMapping(DELETE_DONATION_USER_BY_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteDonationById(@PathVariable("donationId") Long id, Principal principal){
		donationService.deleteDonation(id, principal.getName());
		return ResponseEntity.ok(HttpStatus.NO_CONTENT);
	}
	
	//ANY
	
	@GetMapping(GET_ALL_DONATION_USER_DONT_PLAY)
	public ResponseEntity<?> getAllDonationFromUser(@PathVariable("userName") String username){
		return ResponseEntity.ok(donationService.getDonationFromUserAndPlay(username, false));
	}
	
	

	
}
