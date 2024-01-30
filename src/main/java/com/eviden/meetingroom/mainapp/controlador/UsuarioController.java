package com.eviden.meetingroom.mainapp.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.meetingroom.exceptions.exception.BadRequestException;
import com.eviden.meetingroom.exceptions.exception.DataNotFoundException;
import com.eviden.meetingroom.mainapp.DTO.UsuarioRequest;
import com.eviden.meetingroom.mainapp.modelo.entity.Usuario;
import com.eviden.meetingroom.mainapp.servicios.IUsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "/user")
@Log4j2
@CrossOrigin(origins = "*", methods = { RequestMethod.POST,RequestMethod.DELETE,RequestMethod.GET, RequestMethod.PUT})
public class UsuarioController {

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	IUsuarioService usuarioService;
	
	@PostMapping(path = "/new")
	public ResponseEntity<Usuario> newUser(@RequestBody UsuarioRequest newUsuario) throws JsonProcessingException {
		log.info("**[MeetingRoom]--- Estamos dando de alta un nuevo usuario en el sistema");
		try {
			Usuario userCreate = Usuario.builder()
											.email(newUsuario.getEmail())
			         						.password(newUsuario.getPassword())
		         							.estadoUser(true)
		         							.build();
			
			Usuario userAux = usuarioService.newUser(userCreate);
			String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userAux);
			log.info("**[MeetingRoom]--- El equipo creado es: " + prettyJsonString);
			
			return new ResponseEntity<Usuario>(userAux,HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			String msgError = "";
			if(e instanceof DataIntegrityViolationException){
				//log.info("Excepcion en new User: " + e.getMessage().split("Detail:")[1]);
				msgError = e.getRootCause().getLocalizedMessage().split("Detail: ")[1];
			}else {
				//log.info("Excepcion en new User: " + e.getMessage());
				msgError = e.getMessage();
			}
			throw  new BadRequestException("USR-001",msgError);
		} 
	}
	
	@GetMapping(path = "/{email}")
	public ResponseEntity<Usuario> findUsuario(@PathVariable("email") String email){
		log.info("**[MeetingRoom]--- Buscando el usuario con email: " + email);
		Usuario userAux = usuarioService.buscoPorEmail(email)
				       .orElseThrow(()-> new DataNotFoundException("USR-002", "El Usuario con email: " + email + " NO esta dado de alta en el sistema"));
		return new ResponseEntity<Usuario>(userAux, HttpStatus.OK);
	}
	
	@GetMapping(path = "/")
	public ResponseEntity<?> findAllUsuario(){
		log.info("**[MeetingRoom]--- Buscando todos los usuarios que hay en el sistema");
		List<Usuario> userAux = usuarioService.allUsuarios()
				       .orElseThrow(()-> new DataNotFoundException("USR-006", "No hay usuarios dados de alta en el sistema"));
		return new ResponseEntity<List<Usuario>>(userAux, HttpStatus.OK);
	}
	
	@GetMapping(path = "/activos")
	public ResponseEntity<?> findAllUsuarioActivos(){
		log.info("**[MeetingRoom]--- Buscando todos los usuarios activos que hay en el sistema");
		List<Usuario> userAux = usuarioService.usuariosActivos(true)
				       .orElseThrow(()-> new DataNotFoundException("USR-009", "No hay usuarios activos dados de alta en el sistema"));
		return new ResponseEntity<List<Usuario>>(userAux, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/delete/{email}")
	public ResponseEntity<?> borroUser(@PathVariable String email){
		Map<String, Object> response = new HashMap<>();
		log.info("**[MeetingRoom]--- Estamos intentando dar de baja a un usuario de la BBDD");
		
		try {
			Usuario userAux = usuarioService.buscoPorEmail(email)
					  .orElseThrow(() -> new DataNotFoundException("USR-003", "No se encuentra el usuario " + email + " dado de alta en el sistema"));
			
			usuarioService.deleteUser(userAux);
			response.put("Mensaje","Equipo borrado correctamente");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		} catch (DataAccessException e) {
			throw  new BadRequestException("USR-004",e.getMessage());
		}
	}
	
	@PutMapping(path = "/updtpass")
	public ResponseEntity<?> updtPass(@RequestBody UsuarioRequest userUpdt){
		Map<String, Object> response = new HashMap<>();
		log.info("**[MeetingRoom]--- Estamos modificando los datos de un usuario");
		try {
			Usuario userAux = usuarioService.buscoPorEmail(userUpdt.getEmail())
					  .orElseThrow(() -> new DataNotFoundException("USR-005", "No se encuentra el usuario " + userUpdt.getEmail() + " dado de alta en el sistema"));
			//En este caso solo actualizamos la password del usuario
			userAux.setPassword(userUpdt.getPassword());
			usuarioService.newUser(userAux);
			response.put("Mensaje","Usuario modificado correctamente");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			throw  new BadRequestException("USR-007",e.getMessage());
		}
	}
	
	@PutMapping(path = "/delLog/{activar}/{email}")
	public ResponseEntity<?> updtPass(@PathVariable boolean activar, @PathVariable String email){
		Map<String, Object> response = new HashMap<>();
		log.info("**[MeetingRoom]--- Estamos modificando los datos de un usuario");
		try {
			Usuario userAux = usuarioService.buscoPorEmail(email)
					  .orElseThrow(() -> new DataNotFoundException("USR-008", "No se encuentra el usuario " + email + " dado de alta en el sistema"));
			//En este caso solo actualizamos el estado del usuario. Se da de alta/baja logica al usuario.
			userAux.setEstadoUser(activar);
			usuarioService.newUser(userAux);
			response.put("Mensaje","Usuario modificado correctamente");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			throw  new BadRequestException("USR-009",e.getMessage());
		}
	}
}
