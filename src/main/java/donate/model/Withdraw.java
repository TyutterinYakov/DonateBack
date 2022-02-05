package donate.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="withdraw")
public class Withdraw {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dateWithdraw;
	private BigDecimal summWithdraw;
	@Enumerated(value = EnumType.STRING)
	private Status status=Status.SUCCESS;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JsonIgnore
	private User user;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDateWithdraw() {
		return dateWithdraw;
	}
	public void setDateWithdraw(LocalDateTime dateWithdraw) {
		this.dateWithdraw = dateWithdraw;
	}
	public BigDecimal getSummWithdraw() {
		return summWithdraw;
	}
	public void setSummWithdraw(BigDecimal summWithdraw) {
		this.summWithdraw = summWithdraw;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}
