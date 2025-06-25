package com.feme.sec365.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feme.sec365.exceptions.ResourceNotFoundException;
import com.feme.sec365.models.AppRole;
import com.feme.sec365.models.Role;
import com.feme.sec365.models.User;
import com.feme.sec365.repositories.RoleRepository;
import com.feme.sec365.repositories.UserRepository;
import com.feme.sec365.security.jwt.JwtUtils;
import com.feme.sec365.security.request.SignupRequest;
import com.feme.sec365.security.response.MessageResponse;
import com.feme.sec365.security.response.UserInfoResponse;
import com.feme.sec365.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired 
	PasswordEncoder encoder;
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}
		
		User userToRegister = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				signUpRequest.getPhone(),
				encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
	
		if(strRoles == null) {
			Role userRole = roleRepository.findByRoleByName(AppRole.ROLE_USER)
					.orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch(role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleByName(AppRole.ROLE_ADMIN)
						.orElseThrow(() -> new ResourceNotFoundException("Role ADMIN not found!"));
					roles.add(adminRole);
				
					break;
				default:
					Role userRole = roleRepository.findByRoleByName(AppRole.ROLE_USER)
							.orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
					roles.add(userRole);
				}
			});
		}
		
		userToRegister.setRoles(roles);
		userRepository.save(userToRegister);
		
		return ResponseEntity.ok(new MessageResponse("User Registered successfully!"));
	}

	@GetMapping("/username")
	public String getUserName(Authentication authentication) {
		if(authentication != null) {
			return authentication.getName();
		}
		return "";
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUserDetails(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		UserInfoResponse response = new UserInfoResponse(userDetails.getUser_id(), userDetails.getUsername(), roles);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> signoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}














