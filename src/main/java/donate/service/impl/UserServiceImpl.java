package donate.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import donate.exception.NotPermissionException;
import donate.exception.UserFoundException;
import donate.exception.UserNotFoundException;
import donate.model.User;
import donate.repository.UserRepository;
import donate.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userDao;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	

	@Autowired
	public UserServiceImpl(UserRepository userDao, UserDetailsServiceImpl userDetailsServiceImpl) {
		super();
		this.userDao = userDao;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	public void updateUser(User user, String userName) throws UserNotFoundException, NotPermissionException, UserFoundException {
		User us = userDao.findByUserName(userName).orElseThrow(()->
			new UserNotFoundException());
		if(us.getUserId().equals(user.getUserId())) {
			us.setEmail(user.getEmail());
			if(user.getPassword()!=null&&user.getPassword().trim()!="") {
				us.setPassword(userDetailsServiceImpl.passwordEncoder().encode(user.getPassword()));
			}
			if(!us.getUserName().equals(user.getUserName())) {
				if(userDao.findByUserName(user.getUserName()).isPresent()) {
					throw new UserFoundException();
				} 
				us.setUserName(user.getUserName());
				
			}
			userDao.save(us);
		} else {
			throw new NotPermissionException();
		}
	}

	public void deleteUser(String userName) throws UserNotFoundException {
		User us = userDao.findByUserName(userName).orElseThrow(()->
			new UserNotFoundException());
		userDao.delete(us);
	}

	@Override
	public User findUserByUserName(String userName) throws UserNotFoundException{
		Optional<User> userOptional = userDao.findByUserName(userName);
		if(userOptional.isPresent()) {
		return userOptional.get();
		}
		throw new UserNotFoundException();
	}

}
