package donate.controller;

import java.security.Principal;

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

import donate.exception.UserNotFoundException;
import donate.service.PersonalizationDonateAlertService;

@RestController
@RequestMapping("/widgets")
@CrossOrigin("*")
public class WidgetController {
	
	private PersonalizationDonateAlertService personalizationService;

	@Autowired
	public WidgetController(PersonalizationDonateAlertService personalizationService) {
		super();
		this.personalizationService = personalizationService;
	}
	
	@GetMapping("/")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getWidgets(Principal principal){
		
		try {
			return ResponseEntity.ok(personalizationService.getAllPersonalizationByUser(principal.getName()));
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>("Нет такого пользователя",HttpStatus.FORBIDDEN);
		} catch(NullPointerException ex) {
			return new ResponseEntity<>("Нет имени пользователя", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteWidgets(@PathVariable Long id,Principal principal){
		
		try {
			personalizationService.deletePersonalization(id, principal.getName());
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>("Нет такого пользователя",HttpStatus.FORBIDDEN);
		} catch(NullPointerException ex) {
			return new ResponseEntity<>("Нет имени пользователя", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
