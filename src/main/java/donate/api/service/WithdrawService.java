package donate.api.service;

import java.math.BigDecimal;
import java.util.List;

import donate.api.exception.UserNotFoundException;
import donate.store.entity.Withdraw;

public interface WithdrawService {
	
	public Withdraw createWithdraw(String userName, BigDecimal summWithdraw);
	public List<Withdraw> getAllWithdraw(String userName);
	
}
