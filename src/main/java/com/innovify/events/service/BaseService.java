package com.innovify.events.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.innovify.events.repository.EventRegistrationRepo;
import com.innovify.events.repository.EventRepo;
import com.innovify.events.repository.UserRegistrationRepo;

public class BaseService {
	
	@Autowired
	protected FileStorageService fss;
	
	@Autowired
	protected UserRegistrationRepo userRegistrationRepo;
	
	@Autowired
	protected EventRepo eventRepo;
	
	@Autowired
	protected EventRegistrationRepo eventRegistrationRepo;
	
}
