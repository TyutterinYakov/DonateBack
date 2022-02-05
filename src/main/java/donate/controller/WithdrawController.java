package donate.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import donate.exception.InvalidDataException;
import donate.exception.UserNotFoundException;
import donate.service.WithdrawService;

@RestController
@RequestMapping("/withdraw")
@CrossOrigin("*")
public class WithdrawController {

	private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);
	private WithdrawService withdrawService;
	
	@Autowired
	public WithdrawController(WithdrawService withdrawService) {
		super();
		this.withdrawService = withdrawService;
	}
	
	
	@PostMapping("/")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addWithdraw(@RequestBody BigDecimal summWithdraw, Principal principal){
		
		try {
			if(summWithdraw.compareTo(new BigDecimal(50))==-1) {
				throw new InvalidDataException();
			}
			return ResponseEntity.ok(withdrawService.createWithdraw(principal.getName(), summWithdraw));
		} catch(NullPointerException ex) {
			logger.error("Нет имени пользователя", ex);
			return new ResponseEntity<>("Необходима переавторизация", HttpStatus.FORBIDDEN);
		} catch (UserNotFoundException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("Пользователь не найден", HttpStatus.FORBIDDEN);
		} catch (InvalidDataException e) {
			logger.error(summWithdraw.toString(), e);
			return new ResponseEntity<>("Сумма меньше минимальной", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllWithdrawUser(Principal principal){
		try {
			return ResponseEntity.ok(withdrawService.getAllWithdraw(principal.getName()));
		} catch (NullPointerException ex) {
			logger.error("Нет имени пользователя", ex);
			return new ResponseEntity<>("Необходима переавторизация", HttpStatus.FORBIDDEN);
		} catch (UserNotFoundException e) {
			logger.error(principal.getName(), e);
			return new ResponseEntity<>("Пользователь не найден", HttpStatus.FORBIDDEN);
		} 
	}
	
	
}
