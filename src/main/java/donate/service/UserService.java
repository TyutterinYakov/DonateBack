package donate.service;

import donate.model.User;

public interface UserService {
	User createUser(User user);
	User updateUser(User user);
	void deleteUser(Long userId);

}
