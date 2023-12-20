package com.eviden.meetingroom.mainapp.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.meetingroom.exceptions.exception.BadRequestException;
import com.eviden.meetingroom.exceptions.exception.DataNotFoundException;
import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.mainapp.servicios.IRolService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/rol")
@Log4j2
public class RolController {

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	IRolService rolService;
	
	@PostMapping(path = "/new")
	public ResponseEntity<?> newRol(@RequestBody Rol newRol) throws JsonProcessingException{
		log.info("**[MeetingRoom]--- Estamos creando un rol nuevo");
		
		//Transformamos el objeto JSON en un string para mostrarlo por consola.
		String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newRol);
		log.info("**[MeetingRoom]--- Rol: " + prettyJsonString);
		try {
			return new ResponseEntity<>(rolService.generoRol(newRol), HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			throw  new BadRequestException("ROL-001",e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/delete/{idRol}")
	public ResponseEntity<?> borroRol(@PathVariable("idRol") int id) throws JsonProcessingException{
		log.info("**[MeetingRoom]--- Estamos borrando un rol con ID: " + id);
		try {
			Rol rolAux = rolService.buscoRol(id).
					orElseThrow(()-> new DataNotFoundException("ROL-002", "No se encuentra el Rol con id: " + id + " dado de alta en el sistema"));
			
			//Transformamos el objeto JSON en un string para mostrarlo por consola.
			String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rolAux);
			log.info("**[MeetingRoom]--- Rol: " + prettyJsonString);
			
			rolService.borroRol(rolAux);
			return new ResponseEntity<String>("Rol borrado correctamente",HttpStatus.OK);
		} catch (DataAccessException e) {
			throw  new BadRequestException("ROL-003",e.getMessage());
		}
	}
}
