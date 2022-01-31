package donate.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import donate.model.User;

public class SecurityUser implements UserDetails {

	private final String username;
	private final String password;
	private final List<SimpleGrantedAuthority> authorities;
	private final boolean isActive;
	
	
	public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isActive = isActive;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return isActive;
	}

	public boolean isAccountNonLocked() {
		return isActive;
	}

	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	public boolean isEnabled() {
		return isActive;
	}

	public static UserDetails fromUser(User user) {
		
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				user.isActive(),
				user.isActive(),
				user.isActive(),
				user.isActive(),
				user.getRole().getAuthorities());
	}

}
