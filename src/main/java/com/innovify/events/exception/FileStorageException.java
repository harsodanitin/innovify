package com.innovify.events.exception;

public class FileStorageException extends EventException {

	private static final long serialVersionUID = 6422265836992923157L;
	
	private static final int ERROR_CODE = 101;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public int getErrorCode(){
		return ERROR_CODE;
	}


}
