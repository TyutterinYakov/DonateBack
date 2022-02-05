package donate.service;

import java.math.BigDecimal;
import java.util.List;

import donate.exception.UserNotFoundException;
import donate.model.Withdraw;

public interface WithdrawService {
	
	public Withdraw createWithdraw(String userName, BigDecimal summWithdraw) throws UserNotFoundException;
	public List<Withdraw> getAllWithdraw(String userName) throws UserNotFoundException;
	
}
