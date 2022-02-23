package donate.api.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import donate.api.dto.UserDto;
import donate.api.dto.factory.UserDtoFactory;
import donate.api.exception.UserFoundException;
import donate.api.model.UserModel;
import donate.api.security.SecurityUser;
import donate.store.entity.UserEntity;
import donate.store.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	private final UserRepository userDao;
	private final UserDtoFactory userDtoFactory;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userDao, UserDtoFactory userDtoFactory) {
		super();
		this.userDao = userDao;
		this.userDtoFactory = userDtoFactory;
	}

	public UserDto getUser(String userName) {
		return userDtoFactory.createUserDto(findUserByUserName(userName));
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userDao.findByUserName(username).orElseThrow(()->
				new UsernameNotFoundException(username));
		return SecurityUser.fromUser(user);
	}
	
	public UserDto createUser(UserModel userModel) throws UserFoundException {
		
		userDao.findByUserName(userModel.getUserName()).ifPresent((us)->{
			throw new UserFoundException(
					String.format(
							"Пользователь с ником \"%s\" уже зарегистрирован ",
							userModel.getUserName()));}
		);
		userModel.setPassword(this.passwordEncoder().encode(userModel.getPassword()));
		
		
		return userDtoFactory.createUserDto(
				userDao.saveAndFlush(
						new UserEntity(
								userModel.getUserName(), 
								userModel.getPassword(), 
								userModel.getEmail()))
				);
	}

	
	
	
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder(12);
	}
	
	public UserEntity findUserByUserName(String userName) {
		return userDao.findByUserName(userName).orElseThrow(()->
			new UsernameNotFoundException(String.format("Пользователь с ником \"%s\" не найден", userName)));
	}


}
