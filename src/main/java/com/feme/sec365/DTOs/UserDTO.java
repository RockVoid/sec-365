package com.feme.sec365.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

	@NotBlank
	@Size(min = 3, max = 30, message = "Username must have between 3 and 30 characters!")
	private String name;
	
	@NotBlank
	@Size(min = 3, max = 20, message = "Username must have between 3 and 20 characters!")
	private String username;
	
	@NotBlank
	@Email
	private String email;
	
    @Size(min = 8, max = 20, message = "Phone must have between 8 and 20 characters!")
    @Pattern(regexp = "^[\\d\\s\\+\\(\\)\\-]+$", message = "Phone have invalid characters!")
	private String phone;

    // Pending AWS...
	private String profile_pic;
}
