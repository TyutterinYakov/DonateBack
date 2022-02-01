package donate.model;

import java.math.BigDecimal;

public class DonationRequest {
	
	public String username;
	public String donateName;
	public String message;
	public BigDecimal summ;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDonateName() {
		return donateName;
	}
	public void setDonateName(String donateName) {
		this.donateName = donateName;
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
	
	
}
