package com.innovify.events.exception;

public class UnsupportedFileTypeException extends EventException {

	private static final long serialVersionUID = -6137841228316097079L;
	
	private static final int ERROR_CODE = 103;

	public UnsupportedFileTypeException(String message) {
		super(message);
	}

	public UnsupportedFileTypeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public int getErrorCode(){
		return ERROR_CODE;
	}


}
