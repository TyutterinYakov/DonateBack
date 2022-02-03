package donate.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.NotPermissionException;
import donate.exception.UserFoundException;
import donate.exception.UserNotFoundException;
import donate.model.User;
import donate.service.UserService;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	private UserService userService;

	@Autowired
	public ProfileController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PutMapping("/")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> updateProfile(@RequestBody User user, Principal principal) {
		
		try {
			userService.updateUser(user, principal.getName());
		} catch (NullPointerException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("Требуется переавторизация", HttpStatus.FORBIDDEN);
		} catch (UserNotFoundException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("Требуется переавторизация", HttpStatus.FORBIDDEN);
		} catch (NotPermissionException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("У вас нет прав на изменение этого пользователя", HttpStatus.FORBIDDEN);
		} catch (UserFoundException e) {
			logger.error(user.getUserName(), e);
			return new ResponseEntity<>("Такой  ник занят", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteProfile(Principal principal){
		try {
			userService.deleteUser(principal.getName());
		} catch(NullPointerException ex) {
			logger.error("Нет данных principal", ex);
			return new ResponseEntity<>("Перезайдите в систему", HttpStatus.FORBIDDEN);
		} catch (UserNotFoundException e) {
			logger.error("Пользователь не найден", e);
			return new ResponseEntity<>("Перезайдите в систему", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
