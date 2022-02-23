package donate.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.api.exception.UserNotFoundException;
import donate.api.service.WithdrawService;
import donate.store.entity.Status;
import donate.store.entity.User;
import donate.store.entity.Withdraw;
import donate.store.repository.UserRepository;
import donate.store.repository.WithdrawRepository;

@Service
public class WithdrawServiceImpl implements WithdrawService{

	private UserRepository userDao;
	private WithdrawRepository withdrawDao;
	
	@Autowired
	public WithdrawServiceImpl(UserRepository userDao, WithdrawRepository withdrawDao) {
		super();
		this.userDao = userDao;
		this.withdrawDao = withdrawDao;
	}

	@Override
	public Withdraw createWithdraw(String userName, BigDecimal summWithdraw) throws UserNotFoundException {
		User user = getUserByUsername(userName);
		Withdraw withdraw = new Withdraw();
		withdraw.setDateWithdraw(LocalDateTime.now());
		withdraw.setSummWithdraw(summWithdraw);
		withdraw.setUser(user);
		if(user.getBalance().compareTo(summWithdraw)==-1) {
			withdraw.setStatus(Status.ERROR);
		} else {
			withdraw.setStatus(Status.SUCCESS);
			user.setWithDrawSumm(summWithdraw.add(user.getWithDrawSumm()));
			user.setBalance(user.getBalance().subtract(summWithdraw));
			user.setWithDrawCount(user.getWithDrawCount()+1L);
		}
		return withdrawDao.save(withdraw);
	}

	@Override
	public List<Withdraw> getAllWithdraw(String userName) throws UserNotFoundException {
		return withdrawDao.findAllByUserOrderByDateWithdraw(getUserByUsername(userName));
	}
	
	
	
	
	private User getUserByUsername(String userName) throws UserNotFoundException {
		return userDao.findByUserName(userName).orElseThrow(()->
		new UserNotFoundException()
	);
	}

}
