package donate.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import donate.exception.InvalidDataException;
import donate.exception.NotPermissionException;
import donate.exception.UserFoundException;
import donate.exception.UserNotFoundException;
import donate.model.User;
import donate.repository.UserRepository;
import donate.service.UserService;
import donate.util.UploadAndRemoveImage;

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

	public void updateUser(User user, String userName) throws UserNotFoundException, NotPermissionException, UserFoundException, InvalidDataException {
		User us = getUserByUsername(userName);
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
				throw new InvalidDataException();
			}
			us.setMinSummDonate(user.getMinSummDonate());
			userDao.save(us);
		} else {
			throw new NotPermissionException();
		}
	}


	public void deleteUser(String userName) throws UserNotFoundException {
		User us = getUserByUsername(userName);
		String image = us.getProfileImage();
		userDao.delete(us);
		imageUtil.deleteImage(image, "profile/");
		
	}

	@Override
	public User findUserByUserName(String userName) throws UserNotFoundException{
		User user = getUserByUsername(userName);
		return user;
	}

	@Override
	public BigDecimal getMinSummDonateFromUserName(String userName) throws UserNotFoundException {
		User us = getUserByUsername(userName);
		return us.getMinSummDonate();
	}

	@Override
	public void updateImageProfile(String name, MultipartFile file) throws UserNotFoundException, IOException {
		User user = getUserByUsername(name);
		imageUtil.deleteImage(user.getProfileImage(), "profile/");
		String fileName = imageUtil.uploadImage(file, "profile");
		user.setProfileImage(fileName);
		userDao.save(user);
	}

	@Override
	public byte[] getImageProfile(String name) throws UserNotFoundException, IOException{
		User user = getUserByUsername(name);
		return imageUtil.getImage(user.getProfileImage(), "profile/");
	}
	
	private User getUserByUsername(String userName) throws UserNotFoundException {
		return userDao.findByUserName(userName).orElseThrow(()->
		new UserNotFoundException());
	}


}
