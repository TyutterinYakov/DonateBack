package donate.exception;

public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super("this UserName is not from db");
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
