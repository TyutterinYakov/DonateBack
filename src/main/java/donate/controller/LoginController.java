package donate.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.InvalidDataException;
import donate.exception.UserFoundException;
import donate.exception.UserNotFoundException;
import donate.model.AuthRequest;
import donate.model.User;
import donate.service.impl.LoginServiceImpl;
import donate.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping
@CrossOrigin("*")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private UserDetailsServiceImpl userDetailsService;
	private LoginServiceImpl loginService;
	
	@Autowired
	public LoginController(UserDetailsServiceImpl userDetailsService, LoginServiceImpl loginService) {
		super();
		this.userDetailsService = userDetailsService;
		this.loginService = loginService;
	}
	
	@PostMapping("/generate-token")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) throws UserNotFoundException{
		try {
			return ResponseEntity.ok(loginService.getAuthenticate(request));
		} catch(AuthenticationException ex) {
			logger.error(request.getUserName(), ex);
			return new ResponseEntity<>("Неправильный логин/пароль", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response){
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, null);
	}
	
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		logger.info(principal.getName());
		return userDetailsService.getUser(principal.getName());
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult result) throws InvalidDataException {
		try {
			if(result.hasErrors()) {
				throw new InvalidDataException();
			}
				return ResponseEntity.ok(userDetailsService.createUser(user));
		} catch(UserFoundException ex) {
			logger.error(user.toString(), ex);
			return new ResponseEntity<>("Такой пользователь уже есть", HttpStatus.BAD_REQUEST);
		}
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
