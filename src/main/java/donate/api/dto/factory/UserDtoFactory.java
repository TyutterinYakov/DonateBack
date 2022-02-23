package donate.api.dto.factory;

import org.springframework.stereotype.Component;

import donate.api.dto.UserDto;
import donate.store.entity.UserEntity;

@Component
public class UserDtoFactory {

	
	public UserDto createUserDto(UserEntity entity) {
		return new UserDto(
				entity.getUserName(), 
				entity.getEmail(), 
				entity.getMinSummDonate(), 
				entity.getBalance(),
				entity.getRole(),
				entity.getProfileImage()
				);
	}
}
