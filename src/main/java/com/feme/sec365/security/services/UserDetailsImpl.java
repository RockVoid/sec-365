package com.feme.sec365.security.services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feme.sec365.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1;
	
	private UUID user_id;
	private String name;
	private String username;
	private String email;
	private String phone;
	
	@JsonIgnore
	private String password;
	private String profile_pic;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(UUID user_id, String name, String username, String email, 
			String phone, String password, Collection<? extends GrantedAuthority> authorities) {
		this.user_id = user_id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		
		List<GrantedAuthority> authorities = user.getRoles() .stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(
				user.getUser_id(),
				user.getName(),
				user.getUsername(),
				user.getEmail(),
				user.getPhone(),
				user.getPassword(),
				authorities
			);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
}
