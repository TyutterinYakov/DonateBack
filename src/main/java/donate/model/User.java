package donate.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	@Column(name="user_name")
	@Size(min=3, max=25)
	private String userName;
	@Column(name="password")
	@Size(min=5, max=25)
	private String password;
	@Column(name="email")
	@Email
	@Size(min=5, max=25)
	private String email;
	private boolean active=true;
	private String profileImage;
	private BigDecimal balance=new BigDecimal(0);
	private BigDecimal allTimeMoney=new BigDecimal(0);
	private Long countMessage = 0L;
	private BigDecimal minSummDonate=new BigDecimal(1);
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Donation> donations;
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<PersonalizationDonateAlert> personalizations;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	
	
	public User() {
		super();
	}
	public User(String userName, String password, @Email String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public List<Donation> getDonations() {
		return donations;
	}
	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}
	public List<PersonalizationDonateAlert> getPersonalizations() {
		return personalizations;
	}
	public void setPersonalizations(List<PersonalizationDonateAlert> personalizations) {
		this.personalizations = personalizations;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public BigDecimal getAllTimeMoney() {
		return allTimeMoney;
	}
	public void setAllTimeMoney(BigDecimal allTimeMoney) {
		this.allTimeMoney = allTimeMoney;
	}
	public Long getCountMessage() {
		return countMessage;
	}
	public void setCountMessage(Long countMessage) {
		this.countMessage = countMessage;
	}
	public BigDecimal getMinSummDonate() {
		return minSummDonate;
	}
	public void setMinSummDonate(BigDecimal minSummDonate) {
		this.minSummDonate = minSummDonate;
	}
	
	
	
	
	
	
	
}
