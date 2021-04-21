package com.ciro.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErroException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	public ErroException(String message) {
		super(message);
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	public ErroException(String message, HttpStatus badRequest) {
		super(message);
		this.httpStatus = badRequest;
	}

}