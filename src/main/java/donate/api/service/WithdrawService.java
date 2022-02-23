package donate.api.service;

import java.math.BigDecimal;
import java.util.List;

import donate.api.exception.UserNotFoundException;
import donate.store.entity.WithdrawEntity;

public interface WithdrawService {
	
	public WithdrawEntity createWithdraw(String userName, BigDecimal summWithdraw);
	public List<WithdrawEntity> getAllWithdraw(String userName);
	
}
