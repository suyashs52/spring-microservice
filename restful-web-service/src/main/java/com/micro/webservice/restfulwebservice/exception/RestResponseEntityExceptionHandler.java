package com.micro.webservice.restfulwebservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleErrorResponseException(Exception ex, WebRequest request) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(err, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(),
				" Total Error(s): " + ex.getErrorCount() + " First " + ex.getFieldError().getDefaultMessage(),
				request.getDescription(false));

		return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);

	}

}
