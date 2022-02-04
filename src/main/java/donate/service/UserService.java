package donate.service;

import java.math.BigDecimal;

import donate.exception.InvalidDataException;
import donate.exception.NotPermissionException;
import donate.exception.UserFoundException;
import donate.exception.UserNotFoundException;
import donate.model.User;

public interface UserService {
	public void updateUser(User user, String userName) throws UserNotFoundException, NotPermissionException, UserFoundException, InvalidDataException;
	public void deleteUser(String string) throws UserNotFoundException;
	public User findUserByUserName(String userName) throws UserNotFoundException;
	public BigDecimal getMinSummDonateFromUserName(String userName) throws UserNotFoundException;

}
