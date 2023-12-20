package com.eviden.meetingroom.mainapp.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.meetingroom.exceptions.exception.BadRequestException;
import com.eviden.meetingroom.exceptions.exception.DataNotFoundException;
import com.eviden.meetingroom.mainapp.modelo.entity.Equipo;
import com.eviden.meetingroom.mainapp.servicios.IEquipoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/equipo")
@Log4j2
public class EquipoController {

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	IEquipoService equipoService;
	
	@PostMapping(path = "/new")
	public ResponseEntity<?> nuevoEquipo(@RequestBody Equipo newEquipo) throws JsonProcessingException{
		log.info("**[MeetingRoom]--- Estamos dando de alta un equipo nuevo en el sistema");
		
		try {
			Equipo equipoAux = equipoService.generoEquipo(newEquipo);
			String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(equipoAux);
			log.info("**[MeetingRoom]--- El equipo a crear es: " + prettyJsonString);
			return new ResponseEntity<Equipo>(equipoAux,HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			throw  new BadRequestException("EQP-001",e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/delete/{idEquipo}")
	public ResponseEntity<?> borroEquipo(@PathVariable("idEquipo") int id) throws JsonProcessingException{
		log.info("**[MeetingRoom]--- Estamos intentando dar de baja un equipo de la BBDD");
		
		try {
			Equipo equipoAux = equipoService.buscoEqupoId(id)
					.orElseThrow(() -> new DataNotFoundException("EQP-002", "No se encuentra el equipo con id: " + id + " dado de alta en el sistema"));
			
			String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(equipoAux);
			log.info("**[MeetingRoom]--- El equipo a dar de baja es: " + prettyJsonString);
			
			equipoService.borroEquipo(equipoAux);
			
			return new ResponseEntity<String>("Equipo dado de baja correctamente del sistema",HttpStatus.OK);
			
		} catch (DataAccessException e) {
			throw  new BadRequestException("EQP-003",e.getMessage());
		}
	}
	
	@GetMapping(path = "/find/{nombre}")
	public ResponseEntity<?> buscoEqpNombre(@NonNull @PathVariable("nombre") String nom) throws JsonProcessingException {
		log.info("**[MeetingRoom]--- Estamos intentando dar de baja un equipo de la BBDD");
		
		Equipo equipoAux = equipoService.buscoEquipoNombre(nom)
				.orElseThrow(() -> new DataNotFoundException("EQP-004", "El nombre del equipo '" + nom + "' no se encuentra dado de alta en el sistema"));
		
		String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(equipoAux);
		log.info("**[MeetingRoom]--- Se busca este equipo: " + prettyJsonString);
		
		return new ResponseEntity<Equipo>(equipoAux, HttpStatus.OK);
	}
}
