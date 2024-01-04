package com.eviden.meetingroom.exceptions.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmptyRequestException extends RuntimeException{

	
	public EmptyRequestException(String message) {
		super(message);
		System.out.println("mensaje: " + message);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
