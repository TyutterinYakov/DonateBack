package donate.model;

import java.math.BigDecimal;

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
@Table(name="personalization")
public class PersonalizationDonateAlert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="personalization_id")
	private Long personalizationId;
	@Column(name="image")
	private String image;
	@Column(name="summ_min")
	private BigDecimal summMin;
	@Column(name="music")
	private String music;
	private int time;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JsonIgnore
	private User user;
	
	public Long getPersonalizationId() {
		return personalizationId;
	}
	public void setPersonalizationId(Long personalizationId) {
		this.personalizationId = personalizationId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public BigDecimal getSummMin() {
		return summMin;
	}
	public void setSummMin(BigDecimal summMin) {
		this.summMin = summMin;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
	
	

}
