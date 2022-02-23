package donate.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donate.api.exception.UserNotFoundException;
import donate.api.service.WithdrawService;
import donate.store.entity.Status;
import donate.store.entity.UserEntity;
import donate.store.entity.WithdrawEntity;
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
	public WithdrawEntity createWithdraw(String userName, BigDecimal summWithdraw) throws UserNotFoundException {
		UserEntity user = getUserByUsername(userName);
		WithdrawEntity withdraw = new WithdrawEntity();
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
	public List<WithdrawEntity> getAllWithdraw(String userName) throws UserNotFoundException {
		return withdrawDao.findAllByUserOrderByDateWithdraw(getUserByUsername(userName));
	}
	
	
	
	
	private UserEntity getUserByUsername(String userName) throws UserNotFoundException {
		return userDao.findByUserName(userName).orElseThrow(()->
		new UserNotFoundException()
	);
	}

}
