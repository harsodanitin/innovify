package com.innovify.events.exception;

public class EventException extends RuntimeException {
		
	private static final long serialVersionUID = -6188574098079998080L;
	
	private static final int ERROR_CODE = 100;
	
	public EventException(String message){
		super(message);
	}
	
	public EventException(String message, Throwable cause){
		super(message, cause);
	}
	
	public int getErrorCode(){
		return ERROR_CODE;
	}

}
