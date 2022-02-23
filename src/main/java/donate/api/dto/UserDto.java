package donate.api.dto;

import java.math.BigDecimal;

import donate.store.entity.Role;

public class UserDto {
	private String userName;
	private String email;
	private BigDecimal minSummDonate;
	private BigDecimal balance;
	private Role role;
	private String profileImage;
	
	
	public UserDto() {
		super();
	}


	public UserDto(String userName, String email, BigDecimal minSummDonate, BigDecimal balance, Role role,
			String profileImage) {
		super();
		this.userName = userName;
		this.email = email;
		this.minSummDonate = minSummDonate;
		this.balance = balance;
		this.role = role;
		this.profileImage = profileImage;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getMinSummDonate() {
		return minSummDonate;
	}

	public void setMinSummDonate(BigDecimal minSummDonate) {
		this.minSummDonate = minSummDonate;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public String getProfileImage() {
		return profileImage;
	}


	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	
	
	
	
	
}
