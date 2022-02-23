package donate.api.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.api.exception.BadRequestException;
import donate.api.model.AuthRequest;
import donate.api.service.impl.LoginServiceImpl;
import donate.api.service.impl.UserDetailsServiceImpl;
import donate.store.entity.User;

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
	
	public static final String GENERATE_TOKEN_BY_USERNAME_AND_PASSWORD = "/api/auth/generate-token";
	public static final String LOGOUT_USER_BY_HTTP_SERVLET_REQUEST = "/api/auth/logout";
	public static final String GET_CURRENT_USER_BY_PRINCIPAL_USER = "/api/auth/current-user";
	public static final String REGISTER_USER = "/api/auth/register";
	
	@PostMapping(GENERATE_TOKEN_BY_USERNAME_AND_PASSWORD)
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
		return ResponseEntity.ok(loginService.getAuthenticate(request));
	}
	
	@PostMapping(LOGOUT_USER_BY_HTTP_SERVLET_REQUEST)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, null);
	}
	
	@GetMapping(GET_CURRENT_USER_BY_PRINCIPAL_USER)
	public ResponseEntity<User> getCurrentUser(Principal principal) {
		return ResponseEntity.ok(userDetailsService.getUser(principal.getName()));
	}
	
	@PostMapping(REGISTER_USER)
	public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			throw new BadRequestException(
					String.format(
							"Ошибка при вводе данных пользователя: %s", user));
		}
		return ResponseEntity.ok(userDetailsService.createUser(user));
	}
	
	
	
	
}
