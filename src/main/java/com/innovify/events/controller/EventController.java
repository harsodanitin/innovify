package com.innovify.events.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innovify.events.dto.EventDTO;
import com.innovify.events.dto.GenericResponse;
import com.innovify.events.service.EventService;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/api/v1")
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private EventService eventService;
	
	@RequestMapping(value = "/applicationStatus", method = RequestMethod.POST)
	public String applicationStatus() {
		return "Application Running";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public GenericResponse uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("dateFormat") String dateFormat) {
		logger.info("uploadFile api called");
		return eventService.uploadFile(file, dateFormat);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public GenericResponse getUsers() {
		logger.info("getUsers api called");
		return eventService.getUsers();
	}
	
	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public GenericResponse addEvent(@RequestBody EventDTO request) {
		logger.info("addEvent api called");
		return eventService.addEvent(request);
	}
	
	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public GenericResponse getEvent() {
		logger.info("getEvent api called");
		return eventService.getEvent();
	}
	
	@RequestMapping(value = "/eventRegister", method = RequestMethod.POST)
	public GenericResponse eventRegister(@RequestBody EventDTO request) {
		logger.info("eventRegister api called");
		return eventService.eventRegister(request);
	}
}
