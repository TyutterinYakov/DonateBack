package donate.service;

import donate.exception.NotPermissionException;
import donate.exception.UserFoundException;
import donate.exception.UserNotFoundException;
import donate.model.User;

public interface UserService {
	public void updateUser(User user, String userName) throws UserNotFoundException, NotPermissionException, UserFoundException;
	public void deleteUser(String string) throws UserNotFoundException;
	public User findUserByUserName(String userName) throws UserNotFoundException;

}
