package donate.exception;

import org.springframework.http.HttpStatus;

public class UserFoundException extends Exception {

	public UserFoundException() {
		super();
	}
	
	public UserFoundException(String msg) {
		super(msg);
	}
	
	
	
}
