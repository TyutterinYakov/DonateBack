package donate.store.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="donations")
public class DonationEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="donate_id")
	private Long donateId;
	@Column(name="donation_name")
	private String donationName;
	@Column(name="message")
	private String message;
	@Column(name="summ")
	private BigDecimal summ;
	@Column(name="date")
	private LocalDateTime date;
	private boolean play=false;
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	private UserEntity user;
	public Long getDonateId() {
		return donateId;
	}
	public void setDonateId(Long donateId) {
		this.donateId = donateId;
	}
	public String getDonationName() {
		return donationName;
	}
	public void setDonationName(String donationName) {
		this.donationName = donationName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigDecimal getSumm() {
		return summ;
	}
	public void setSumm(BigDecimal summ) {
		this.summ = summ;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public boolean isPlay() {
		return play;
	}
	public void setPlay(boolean play) {
		this.play = play;
	}
	
	
	
	
	
	
	

}
