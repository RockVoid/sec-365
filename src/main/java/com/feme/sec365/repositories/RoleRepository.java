package com.feme.sec365.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feme.sec365.models.AppRole;
import com.feme.sec365.models.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{
	Optional<Role> findByRoleByName(AppRole roleName);
}
