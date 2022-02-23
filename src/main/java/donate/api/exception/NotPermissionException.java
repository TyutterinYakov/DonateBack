package donate.api.exception;

public class NotPermissionException extends RuntimeException {
	public NotPermissionException() {
		super("User dont change!");
	}
	
	public NotPermissionException(String msg) {
		super(msg);
	}

}
