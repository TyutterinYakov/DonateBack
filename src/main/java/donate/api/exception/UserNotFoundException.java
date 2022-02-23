package donate.api.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("this UserName is not from db");
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
