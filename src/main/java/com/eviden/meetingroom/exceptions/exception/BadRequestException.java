package com.eviden.meetingroom.exceptions.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {

	private String code;
	
	public BadRequestException(String code,String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
}
