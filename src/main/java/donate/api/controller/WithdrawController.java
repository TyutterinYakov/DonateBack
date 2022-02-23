package donate.api.controller;

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

import donate.api.exception.BadRequestException;
import donate.api.service.WithdrawService;

@RestController
@RequestMapping
@CrossOrigin("*")
public class WithdrawController {

	private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);
	private WithdrawService withdrawService;
	
	@Autowired
	public WithdrawController(WithdrawService withdrawService) {
		super();
		this.withdrawService = withdrawService;
	}
	
	public static final String ADD_WITHDRAW_BY_PRINCIPAL_USER = "/api/user/withdraw";
	public static final String GET_ALL_WITHDRAWS_BY_PRINCIPAL_USER = "/api/user/withdraw";
	
	
	@PostMapping(ADD_WITHDRAW_BY_PRINCIPAL_USER)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addWithdraw(@RequestBody BigDecimal summWithdraw, Principal principal){
		if(summWithdraw.compareTo(new BigDecimal(50))==-1) 
			throw new BadRequestException("Сумма вывода не может быть меньше минимальной");
		return new ResponseEntity<>(withdrawService.createWithdraw(principal.getName(),
				summWithdraw), HttpStatus.CREATED);
	}
	
	@GetMapping(GET_ALL_WITHDRAWS_BY_PRINCIPAL_USER)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> getAllWithdrawUser(Principal principal){
		return ResponseEntity.ok(withdrawService.getAllWithdraw(principal.getName()));
	}
	
	
}
