package donate.exception;

public class NotPermissionException extends Exception {
	public NotPermissionException() {
		super("User dont change!");
	}
	
	public NotPermissionException(String msg) {
		super(msg);
	}

}
