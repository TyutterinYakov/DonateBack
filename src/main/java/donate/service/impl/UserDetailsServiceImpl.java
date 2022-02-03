package donate.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import donate.exception.UserFoundException;
import donate.model.Role;
import donate.model.User;
import donate.repository.UserRepository;
import donate.security.SecurityUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	private UserRepository userDao;
	
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userDao) {
		super();
		this.userDao = userDao;
	}
	
	public User getUser(String name) throws UsernameNotFoundException {
		loadUserByUsername(name);
		User user = userDao.findByUserName(name).orElseThrow(()->
		new UsernameNotFoundException(name));
		user.setPassword("");
		return user;
	}


	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username).orElseThrow(()->
				new UsernameNotFoundException(username));
		return SecurityUser.fromUser(user);
	}
	
	
	public User createUser(User user) throws UserFoundException {
		
		Optional<User> local = userDao.findByUserName(user.getUserName());
		if(local.isPresent()) {
			
			throw new UserFoundException();
		}
		user.setPassword(this.passwordEncoder().encode(user.getPassword()));
		user.setRole(Role.USER);
		user.setProfileImage("noimage.png");
		userDao.save(user);
		return user;
	}
	
	
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder(12);
	}


}
