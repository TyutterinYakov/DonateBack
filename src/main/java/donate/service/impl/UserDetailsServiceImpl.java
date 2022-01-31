package donate.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
		return user;
	}


	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username).orElseThrow(()->
				new UsernameNotFoundException(username));
		return SecurityUser.fromUser(user);
	}


}
