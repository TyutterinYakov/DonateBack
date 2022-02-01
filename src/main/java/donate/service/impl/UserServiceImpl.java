package donate.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.exception.UserNotFoundException;
import donate.model.User;
import donate.repository.UserRepository;
import donate.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userDao;
	
	@Autowired
	public UserServiceImpl(UserRepository userDao) {
		super();
		this.userDao = userDao;
	}

	public User createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		
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
