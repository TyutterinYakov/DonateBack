package donate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.model.User;
import donate.repository.UserRepository;
import donate.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
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

}
