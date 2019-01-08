package com.innovify.events.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.innovify.events.constants.Constants;
import com.innovify.events.dto.DocFile;
import com.innovify.events.dto.EventDTO;
import com.innovify.events.dto.GenericResponse;
import com.innovify.events.dto.Row;
import com.innovify.events.dto.UserRegistrationDTO;
import com.innovify.events.entities.Event;
import com.innovify.events.entities.EventRegistration;
import com.innovify.events.entities.UserRegistration;
import com.innovify.events.exception.EventException;
import com.innovify.events.parser.ExcelParser;
import com.innovify.events.parser.ParserRequest;

@Service
public class EventService extends BaseService{
	
	private static final Logger logger = LoggerFactory.getLogger(EventService.class);

	
	/** Upload Excel File, Extra Data From Excel and Store in DataBase 
	 * @param file, dateFormat
	 * @return {@link GenericResponse}
	 */
	public GenericResponse uploadFile(MultipartFile file, String dateFormat) {

		GenericResponse response = null;
		
		String filename = fss.storeFile(file);
		logger.info("filename: " + filename);
		
		String fileFullPath = fss.getFileStorageLocation() + "/" + filename;
		logger.info("full filename: " + fileFullPath);
		
		ParserRequest pr = new ParserRequest();
		pr.setFilename(fileFullPath);
		
		ExcelParser parser = new ExcelParser();
		DocFile docFile = parser.parseFile(pr);
		
		// Save Excel Data to Database
		saveDataToDB(docFile, dateFormat);
		
		response = new GenericResponse(0, Constants.SUCCESS, "Data Store Successfully");
		return response;
	}

	private void saveDataToDB(DocFile docFile, String dateFormat){

		List<Row> rows = docFile.getRows();
		logger.info("Number of Record In Excel = " + rows.size());
		for (Row row : rows) {

			if(row.getRowData().size() == 0) {
				continue;
			}
			UserRegistration userRegistration = new UserRegistration();
			userRegistration.setDateOfInterest(convertStringToDate((String) row.getValue("Date of Interest"), dateFormat));
			userRegistration.setFirstName((String) row.getValue("First Name"));
			userRegistration.setLastName((String) row.getValue("Last Name"));
			userRegistration.setDateOfBirth(convertStringToDate((String) row.getValue("Date of Birth"), dateFormat));
			userRegistration.setEmail((String) row.getValue("Email address"));
			userRegistration.setPhoneNo((String) row.getValue("Phone No"));
			userRegistration.setCity((String) row.getValue("City"));
			userRegistration.setLanguage((String) row.getValue("Language"));
			userRegistrationRepo.save(userRegistration);
		}
	}
	
	private Date convertStringToDate(String dateString, String dateFormat) {
		
		logger.info("Date Format = " + dateFormat);
		logger.info("Date String = " + dateString);
		Date date = null;
		try {
			date = new SimpleDateFormat(dateFormat).parse(dateString);
		}catch (Exception e) {
			throw new EventException("date can't be conveted",e);
		}
		return date;
	}

	/** List of All PreRegistered Users
	 * @return {@link GenericResponse}
	 */
	public GenericResponse getUsers() {
		
		GenericResponse response = null;
		
		Iterable<UserRegistration> users = null;
		users = userRegistrationRepo.findAll();
		
		List<UserRegistrationDTO> userDTOs = new ArrayList<>();
		for (UserRegistration userRegistration : users) {
			
			UserRegistrationDTO userDTO = new UserRegistrationDTO();
			userDTO.setDateOfInterest(userRegistration.getDateOfInterest());
			userDTO.setFirstName(userRegistration.getFirstName());
			userDTO.setLastName(userRegistration.getLastName());
			userDTO.setDateOfBirth(userRegistration.getDateOfBirth());
			userDTO.setEmail(userRegistration.getEmail());
			userDTO.setPhoneNo(userRegistration.getPhoneNo());
			userDTO.setCity(userRegistration.getCity());
			userDTO.setLanguage(userRegistration.getLanguage());
			userDTOs.add(userDTO);
		}
		
		response = new GenericResponse(0, Constants.SUCCESS, "Success");
		response.setData(userDTOs);
		return response;
	}

	/** Add an event 
	 * @param request
	 * @return {@link GenericResponse}
	 */
	public GenericResponse addEvent(EventDTO request) {
		
		GenericResponse response = null;
		
		String eventName = request.getName();
		Event event = eventRepo.findByName(eventName);
		if(event != null) {
			logger.info("Event Name already Exist" + eventName);
			throw new EventException("Event Name already exist");
		}
		
		event = new Event();
		event.setName(request.getName());
		event.setDate(request.getDate());
		event.setStartTime(request.getStartTime());
		event.setEndTime(request.getEndTime());
		event.setAddress(request.getAddress());
		event.setCity(request.getCity());
		event.setTotalSeats(request.getTotalSeats());
		event.setRemainingSeats(request.getTotalSeats());
		eventRepo.save(event);
		
		response = new GenericResponse(0, Constants.SUCCESS, "Event Saved Successfully");
		return response;
	}

	/** List of All Events
	 * @return {@link GenericResponse}
	 */
	public GenericResponse getEvent() {
		
		GenericResponse response = null;
		
		Iterable<Event> events = null;
		events = eventRepo.findAll();
		
		List<EventDTO> eventDTOs = new ArrayList<>();
		for (Event event : events) {
		
			EventDTO eventDTO = new EventDTO();
			eventDTO.setId(event.getId());
			eventDTO.setName(event.getName());
			eventDTO.setDate(event.getDate());
			eventDTO.setStartTime(event.getStartTime());
			eventDTO.setEndTime(event.getEndTime());
			eventDTO.setAddress(event.getAddress());
			eventDTO.setCity(event.getCity());
			eventDTO.setTotalSeats(event.getTotalSeats());
			eventDTO.setRemainingSeats(event.getRemainingSeats());
			eventDTOs.add(eventDTO);
		}
		
		response = new GenericResponse(0, Constants.SUCCESS, "Success");
		response.setData(eventDTOs);
		return response;
	}

	/** Registration of User for Specific Event
	 * @param request
	 * @return {@link GenericResponse}
	 */
	public GenericResponse eventRegister(EventDTO request) {
		GenericResponse response = null;
		
		String email = request.getEmail();
		UUID eventId = request.getId();
		
		Optional<Event> eventOpt = eventRepo.findById(eventId);
		if(!eventOpt.isPresent()) {
			
			logger.info("No event found for given id" + email);
			throw new EventException("Something went wrong");
		}
		
		UserRegistration userRegistration = userRegistrationRepo.findByEmail(email);
		if(userRegistration == null) {
			logger.info("Record not found for given email = " + email);
			throw new EventException("Sorry this email is not eligible for registration of event");
			
		}else {
			
			Event event = eventOpt.get();
			
			int availableSeats = event.getRemainingSeats();
			if(availableSeats == 0) {
				throw new EventException("All seats are occupied for selected event");
			}
			
			try {
				EventRegistration eventReg = new EventRegistration();
				eventReg.setUserId(userRegistration.getId());
				eventReg.setEventId(event.getId());
				eventRegistrationRepo.save(eventReg);
				
			}catch (DataIntegrityViolationException e) {
				throw new EventException("User already registered for other event so can't registed with this event");
			}

			event.setRemainingSeats(availableSeats - 1);
			eventRepo.save(event);
		}
		response = new GenericResponse(0, Constants.SUCCESS, "Event Registerd Successfully");
		return response;
	}
}
