package donate.api.exception;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	
	@ExceptionHandler
	public ResponseEntity<?> userNotFoundException(UserNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<?> badRequestExceptionException(BadRequestException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<?> authenticationException(AuthenticationException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler
	public ResponseEntity<?> userFoundException(UserFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FOUND);
	}
	
}
