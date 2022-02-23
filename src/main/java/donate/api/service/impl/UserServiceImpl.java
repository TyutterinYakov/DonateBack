package donate.api.service.impl;

import java.math.BigDecimal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import donate.api.exception.BadRequestException;
import donate.api.exception.NotPermissionException;
import donate.api.exception.UserFoundException;
import donate.api.exception.UserNotFoundException;
import donate.api.service.UserService;
import donate.api.util.UploadAndRemoveImage;
import donate.store.entity.UserEntity;
import donate.store.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userDao;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	private UploadAndRemoveImage imageUtil;
	

	@Autowired
	public UserServiceImpl(UserRepository userDao, UserDetailsServiceImpl userDetailsServiceImpl,
			UploadAndRemoveImage imageUtil) {
		super();
		this.userDao = userDao;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.imageUtil = imageUtil;
	}

	public void updateUser(UserEntity user, String userName) {
		UserEntity us = getUserByUsername(userName);
		if(us.getUserId().equals(user.getUserId())) {
			us.setEmail(user.getEmail());
			if(user.getPassword()!=null&&user.getPassword().trim()!="") {
				us.setPassword(userDetailsServiceImpl.passwordEncoder().encode(user.getPassword()));
			}
			if(!us.getUserName().equals(user.getUserName())) {
				if(userDao.findByUserName(user.getUserName()).isPresent()) {
					throw new UserFoundException();
				} 
				us.setUserName(user.getUserName());
				
			}
			if(user.getMinSummDonate().compareTo(new BigDecimal(1))==-1) {
				throw new BadRequestException();
			}
			us.setMinSummDonate(user.getMinSummDonate());
			userDao.save(us);
		} else {
			throw new NotPermissionException();
		}
	}


	public void deleteUser(String userName) {
		UserEntity us = getUserByUsername(userName);
		String image = us.getProfileImage();
		userDao.delete(us);
		imageUtil.deleteImage(image, "profile/");
		
	}

	@Override
	public UserEntity findUserByUserName(String userName){
		UserEntity user = getUserByUsername(userName);
		return user;
	}

	@Override
	public BigDecimal getMinSummDonateFromUserName(String userName) {
		UserEntity us = getUserByUsername(userName);
		return us.getMinSummDonate();
	}

	@Override
	public void updateImageProfile(String name, MultipartFile file) {
		UserEntity user = getUserByUsername(name);
		imageUtil.deleteImage(user.getProfileImage(), "profile/");
		String fileName = imageUtil.uploadImage(file, "profile");
		user.setProfileImage(fileName);
		userDao.save(user);
	}

	@Override
	public byte[] getImageProfile(String name){
		UserEntity user = getUserByUsername(name);
		return imageUtil.getImage(user.getProfileImage(), "profile/");
	}
	
	private UserEntity getUserByUsername(String userName) {
		return userDao.findByUserName(userName).orElseThrow(UserNotFoundException::new);
	}


}
