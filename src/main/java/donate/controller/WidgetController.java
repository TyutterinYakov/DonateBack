package donate.controller;

import java.math.BigDecimal;
import java.security.Principal;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import donate.exception.UserNotFoundException;
import donate.model.PersonalizationDonateAlert;
import donate.service.PersonalizationDonateAlertService;

@RestController
@RequestMapping("/widgets")
@CrossOrigin("*")
public class WidgetController {
	
	private static final Logger logger = LoggerFactory.getLogger(WidgetController.class);
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
	@PostMapping("/")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addWidgets(@RequestBody PersonalizationDonateAlert widget, Principal principal){
		try {
		return ResponseEntity.ok(personalizationService.addPersonalization(widget, principal.getName()));
		} catch(NullPointerException ex) {
			logger.error("Имя в principal не обнаружено", ex);
			return new ResponseEntity<>("Необходима переавторизация", HttpStatus.FORBIDDEN);
		} catch(UserNotFoundException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("Пользователь не найден", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all/{userName}/{summ}")
	public ResponseEntity<?> getWidgetByUserAndSumm(@PathVariable("userName") String userName, @PathVariable BigDecimal summ){
		
		try {
			if(summ==null||userName.isEmpty()) {
				throw new NullPointerException();
			}
		return ResponseEntity.ok(personalizationService.getWidgetByUserNameAndSumm(userName, summ));
		} catch(UserNotFoundException ex){
			logger.error("Имя неверно", ex);
			return new ResponseEntity<>("Указано неверное имя", HttpStatus.BAD_REQUEST);
		} catch(NullPointerException e) {
			logger.error("Имя или сумма пусты", e);
			return new ResponseEntity<>("Нет имени или суммы", HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> updateWidgets(@RequestBody PersonalizationDonateAlert widget, Principal principal){
		try {
		return ResponseEntity.ok(personalizationService.updatePersonalization(widget, principal.getName()));
		} catch(NullPointerException ex) {
			logger.error("Имя в principal не обнаружено", ex);
			return new ResponseEntity<>("Необходима переавторизация", HttpStatus.FORBIDDEN);
		} catch(UserNotFoundException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("Пользователь не найден", HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/{widgetId}")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getWidget(@PathVariable Long widgetId, Principal principal){
		
		try {
			return ResponseEntity.ok(personalizationService.getPersonalizationByWidgetIdAndUser(widgetId, principal.getName()));
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>("Нет такого пользователя",HttpStatus.FORBIDDEN);
		} catch(NullPointerException ex) {
			return new ResponseEntity<>("Нет имени пользователя", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
