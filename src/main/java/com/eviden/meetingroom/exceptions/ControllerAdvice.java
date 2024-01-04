package com.eviden.meetingroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.eviden.meetingroom.exceptions.exception.BadRequestException;
import com.eviden.meetingroom.exceptions.exception.DataNotFoundException;
import com.eviden.meetingroom.exceptions.exception.EmptyRequestException;

@RestControllerAdvice
public class ControllerAdvice {

	//Datos no encontrados
	@ExceptionHandler(value = DataNotFoundException.class) //Tipo de error que va a tratar
	public ResponseEntity<ErrorDTO> dataNotFoundException(DataNotFoundException ex){
		ErrorDTO errorDto = ErrorDTO.builder()
									  .code(ex.getCode())
									  .mensajeError(ex.getMessage())
				                      .build();
		return new ResponseEntity<ErrorDTO>(errorDto, HttpStatus.NOT_FOUND);
	}
	
	//controla los errores de logica o de los catch en general 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorFormatCampos> handlerBadRequestException(BadRequestException exception,WebRequest webRequest) {
    	ErrorFormatCampos errorFormatCampos = new ErrorFormatCampos(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorFormatCampos, HttpStatus.BAD_REQUEST);
    }
    
  //controla los errores de logica o de los catch en general 400
    @ExceptionHandler(EmptyRequestException.class)
    public ResponseEntity<ErrorFormatCampos> handlerBadRequestException(EmptyRequestException exception,WebRequest webRequest) {
    	ErrorFormatCampos errorFormatCampos = new ErrorFormatCampos(exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorFormatCampos, HttpStatus.BAD_REQUEST);
    }
}
