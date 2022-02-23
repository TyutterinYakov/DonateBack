package donate.api.service;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import donate.store.entity.UserEntity;

public interface UserService {
	public void updateUser(UserEntity user, String userName);
	public void deleteUser(String string);
	public UserEntity findUserByUserName(String userName);
	public BigDecimal getMinSummDonateFromUserName(String userName);
	public void updateImageProfile(String name, MultipartFile file);
	public byte[] getImageProfile(String name);

}
