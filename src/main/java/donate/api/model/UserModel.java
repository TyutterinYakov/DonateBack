package donate.api.model;

import javax.validation.constraints.Size;

public class UserModel {

	@Size(min=3, max=25)
	private String userName;
	private String password;
	@Size(min=5, max=25)
	private String email;
	public UserModel() {
		super();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
