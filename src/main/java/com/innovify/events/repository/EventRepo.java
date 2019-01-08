package com.innovify.events.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovify.events.entities.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, UUID>{

	Event findByName(String eventName);
}
