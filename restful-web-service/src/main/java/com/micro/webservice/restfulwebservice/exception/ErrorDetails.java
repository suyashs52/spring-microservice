package com.micro.webservice.restfulwebservice.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
	
	private LocalDateTime timeStamp;
	private String message;
	private String detail;
	public ErrorDetails(LocalDateTime timeStamp, String message, String detail) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.detail = detail;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	

}
