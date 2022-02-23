package donate.api.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import donate.api.exception.BadRequestException;
import donate.api.exception.UserNotFoundException;
import donate.api.service.PersonalizationDonateAlertService;
import donate.store.entity.WidgetEntity;

@RestController
@RequestMapping
@CrossOrigin("*")
public class WidgetController {
	
	private static final Logger logger = LoggerFactory.getLogger(WidgetController.class);
	private PersonalizationDonateAlertService personalizationService;

	@Autowired
	public WidgetController(PersonalizationDonateAlertService personalizationService) {
		super();
		this.personalizationService = personalizationService;
	}
	
	public static final String GET_WIDGETS_BY_PRINCIPAL_USER = "/api/user/widget";
	public static final String DELETE_WIDGET_BY_PRINCIPAL_USER_AND_WIDGET_ID = "/api/user/widget/{widgetId}";
	public static final String ADD_WIDGET_BY_PRINCIPAL_USER = "/api/user/widget";
	public static final String GET_WIDGET_BY_USERNAME_AND_SUMM_DONATE = "/api/user/widget/donate/{userName}/{summ}";
	public static final String UPDATE_WIDGET_BY_PRINCIPAL_USER = "/api/user/widget";
	public static final String GET_WIDGET_BY_PRINCIPAL_USER_AND_WIDGET_ID = "/api/user/widget/{widgetId}";
	
	@GetMapping(GET_WIDGETS_BY_PRINCIPAL_USER)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getWidgets(Principal principal){
		return ResponseEntity.ok(
				personalizationService
				.getAllPersonalizationByUser(principal.getName()));
	}
	
	@DeleteMapping(DELETE_WIDGET_BY_PRINCIPAL_USER_AND_WIDGET_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteWidgets(@PathVariable("widgetId") Long widgetId,Principal principal){
		personalizationService.deletePersonalization(widgetId, principal.getName());
		return ResponseEntity.ok(HttpStatus.NO_CONTENT);
	}
	@PostMapping(ADD_WIDGET_BY_PRINCIPAL_USER)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addWidgets(@RequestPart("music") MultipartFile music,
			@RequestPart("summMin") String summMin, @RequestPart("time") String time, Principal principal, @RequestPart("image") MultipartFile file){
		WidgetEntity widget = new WidgetEntity();
		widget.setSummMin(new BigDecimal(summMin));
		widget.setTime(Integer.parseInt(time));
		return ResponseEntity.ok(
				personalizationService
				.addPersonalization(widget, principal.getName(), file, music));
	}
	
	@GetMapping(GET_WIDGET_BY_USERNAME_AND_SUMM_DONATE)
	public ResponseEntity<?> getWidgetByUserAndSumm(@PathVariable("userName") String userName, @PathVariable("summ") BigDecimal summ){
		if(summ==null||userName.isEmpty()) {
			throw new BadRequestException("Сумма и имя не могут быть пустыми");
		}
		return ResponseEntity.ok(personalizationService.getWidgetByUserNameAndSumm(userName, summ));
	}
	@PutMapping(UPDATE_WIDGET_BY_PRINCIPAL_USER)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> updateWidgets(@RequestPart("personalizationId") String personalizationId, @RequestPart(name="music", required = false) MultipartFile music,
			@RequestPart("summMin") String summMin, @RequestPart("time") String time, Principal principal, @RequestPart(name="image", required = false) MultipartFile file){
		WidgetEntity widget = new WidgetEntity();
		widget.setPersonalizationId(Long.parseLong(personalizationId));
		widget.setSummMin(new BigDecimal(summMin));
		widget.setTime(Integer.parseInt(time));
		return ResponseEntity.ok(personalizationService.updatePersonalization(widget, principal.getName(), file, music));
	}
	@GetMapping(GET_WIDGET_BY_PRINCIPAL_USER_AND_WIDGET_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getWidget(@PathVariable Long widgetId, Principal principal){
		return ResponseEntity.ok(personalizationService.getPersonalizationByWidgetIdAndUser(widgetId, principal.getName()));
	}
	
	
}
