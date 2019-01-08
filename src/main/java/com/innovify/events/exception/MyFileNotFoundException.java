package com.innovify.events.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends EventException {

	private static final long serialVersionUID = -4649206796954538983L;
	
	private static final int ERROR_CODE = 102;

	public MyFileNotFoundException(String message) {
		super(message);
	}

	public MyFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public int getErrorCode(){
		return ERROR_CODE;
	}


}
