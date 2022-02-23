package donate.api.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import donate.api.exception.BadRequestException;
import donate.api.exception.UserNotFoundException;
import donate.api.service.UserService;
import donate.store.entity.UserEntity;

@RestController
@RequestMapping
@CrossOrigin("*")
public class ProfileController {


	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	private UserService userService;

	@Autowired
	public ProfileController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	public static final String UPDATE_USER_PROFILE_BY_USER_PRINCIPAL = "/api/user/profile";
	public static final String DELETE_USER_PROFILE_BY_USER_PRINCIPAL = "/api/user/profile";
	public static final String UPDATE_OR_ADD_USER_IMAGE_PROFILE_BY_USER_PRINCIPAL = "/api/user/profile/image";
	public static final String GET_USER_IMAGE_PROFILE_BY_USER_PRINCIPAL = "/api/user/profile/image";
	
	@PutMapping(UPDATE_USER_PROFILE_BY_USER_PRINCIPAL)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> updateProfile(@RequestBody UserEntity user, Principal principal) {
		userService.updateUser(user, principal.getName());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(DELETE_USER_PROFILE_BY_USER_PRINCIPAL)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteProfile(Principal principal){
			userService.deleteUser(principal.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(UPDATE_OR_ADD_USER_IMAGE_PROFILE_BY_USER_PRINCIPAL)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addImageProfile(Principal principal, @RequestParam("image") MultipartFile file){
		if(file.isEmpty()) {
			throw new BadRequestException("Файл не передан");
		}
		userService.updateImageProfile(principal.getName(), file);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(GET_USER_IMAGE_PROFILE_BY_USER_PRINCIPAL)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getImageProfile(Principal principal) {
		return ResponseEntity.ok(userService.getImageProfile(principal.getName()));
	}
	
	
}
