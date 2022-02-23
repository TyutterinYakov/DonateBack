package donate.api.service;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import donate.store.entity.User;

public interface UserService {
	public void updateUser(User user, String userName);
	public void deleteUser(String string);
	public User findUserByUserName(String userName);
	public BigDecimal getMinSummDonateFromUserName(String userName);
	public void updateImageProfile(String name, MultipartFile file);
	public byte[] getImageProfile(String name);

}
