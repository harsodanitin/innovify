package com.innovify.events.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovify.events.entities.EventRegistration;

@Repository
public interface EventRegistrationRepo extends CrudRepository<EventRegistration, UUID>{

}
