package donate.exception;

public class InvalidDataException extends Exception {
	public InvalidDataException() {
		super("User dont change!");
	}
	
	public InvalidDataException(String msg) {
		super(msg);
	}
}
