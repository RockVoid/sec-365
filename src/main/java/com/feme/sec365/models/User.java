package com.feme.sec365.models;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
public class User {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID user_id;
	
	@NotBlank
	@Size(min = 3, max = 30, message = "Username must have between 3 and 30 characters!")
	@Column(nullable = false)
	private String name;
	
	@NotBlank
	@Size(min = 3, max = 20, message = "Username must have between 3 and 20 characters!")
	@Column(unique = true, nullable = false)
	private String username;
	
	@NotBlank
	@Email
	@Column(unique = true, nullable = false)
	private String email;
	
    @Column(length = 20)
    @Size(min = 8, max = 20, message = "Phone must have between 8 and 20 characters!")
    @Pattern(regexp = "^[\\d\\s\\+\\(\\)\\-]+$", message = "Phone have invalid characters!")
	private String phone;
    
	@NotBlank
    @Column(length = 20)
    @Size(max = 120)
	private String password;
    
    // Pending AWS...
	private String profile_pic;
}







