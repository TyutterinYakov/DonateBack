package donate.service;

import donate.exception.UserNotFoundException;
import donate.model.User;

public interface UserService {
	public User createUser(User user);
	public User updateUser(User user);
	public void deleteUser(Long userId);
	public User findUserByUserName(String userName) throws UserNotFoundException;

}
