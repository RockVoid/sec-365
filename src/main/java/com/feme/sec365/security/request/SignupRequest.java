package com.feme.sec365.security.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;
	
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
    @Size(min = 8, max = 20, message = "Phone must have between 8 and 20 characters!")
    @Pattern(regexp = "^[\\d\\s\\+\\(\\)\\-]+$", message = "Phone have invalid characters!")
	private String phone;
	
	public Set<String> getRole() {
		return this.role;
	}
	
 	public void setRole(Set<String> role) {
 		this.role = role;
 	}
}
