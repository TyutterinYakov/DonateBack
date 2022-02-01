package donate.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import donate.exception.UserNotFoundException;
import donate.model.AuthRequest;
import donate.model.User;
import donate.security.JwtTokenProvider;
import donate.service.UserService;


@Service
public class LoginServiceImpl {
	
	private final AuthenticationManager authManager;
	private UserService userService;
	private JwtTokenProvider jwtProvider;
	
	@Autowired
	public LoginServiceImpl(AuthenticationManager authManager, UserService userService, JwtTokenProvider jwtProvider) {
		super();
		this.authManager = authManager;
		this.userService = userService;
		this.jwtProvider = jwtProvider;
	}
	
	
	
	@Transactional
	public Map<Object, Object> getAuthenticate(AuthRequest request) throws UserNotFoundException, AuthenticationException {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
			User user = userService.findUserByUserName(request.getUserName());
			if(user==null) {
				throw new UserNotFoundException();
			}
			String token = jwtProvider.createToken(request.getUserName(), user.getRole().name());
			Map<Object, Object> response = new HashMap<>();
			response.put("username", request.getUserName());
			response.put("token", token);
			
			return response;
	}
}
