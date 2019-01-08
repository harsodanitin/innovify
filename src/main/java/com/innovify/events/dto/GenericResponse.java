package com.innovify.events.dto;

public class GenericResponse {
	
	private int errorCode;
	
	private String status;
	
	private String message;
	
	private Object data;
	
	public GenericResponse() {
		
	}
	
	public GenericResponse(int errorCode, String status, String message) {
		this.errorCode = errorCode;
		this.status = status;
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
