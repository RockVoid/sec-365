package com.feme.sec365.Iservices;

import java.util.UUID;

import com.feme.sec365.DTOs.UserDTO;

public interface IUserService {
	void createUser(UserDTO userDTO);
	void updateUser(UUID userId, UserDTO userDTO);
	void deleteUser(UUID userId);
	void GetUsers();
}
