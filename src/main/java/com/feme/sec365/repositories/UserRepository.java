package com.feme.sec365.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feme.sec365.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

}
