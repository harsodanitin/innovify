package com.innovify.events.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovify.events.entities.UserRegistration;

@Repository
public interface UserRegistrationRepo extends CrudRepository<UserRegistration, UUID>{

	UserRegistration findByEmail(String email);
}
