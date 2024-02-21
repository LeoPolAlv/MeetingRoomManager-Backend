package com.eviden.meetingroom.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.meetingroom.exceptions.exception.BadRequestException;
import com.eviden.meetingroom.security.Authorization.JwtUtil;
import com.eviden.meetingroom.security.DTO.AuthenticationRequest;
import com.eviden.meetingroom.security.DTO.AuthenticationResponse;
import com.eviden.meetingroom.security.service.UserAuthService;

/*
* " @Configuration " y " @EnableWebSecurity ". Estas anotaciones le dicen a Spring Security que use nuestra configuración de seguridad personalizada en lugar de la predeterminada
*/
@RestController
@RequestMapping("/auth")
@CrossOrigin(methods = {RequestMethod.GET,RequestMethod.POST}, origins = "*")
public class SecurityController{

	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;

	@ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest loginReq)  {
		 try {
			 System.out.println("Controller del login: " + loginReq.getUsername() + " - " + loginReq.getPassword());
				/*
				* El método authenticManager.authenticate() llamará internamente al método loadUserByUsername() desde nuestra clase CustomUserDetailsService . 
				* Luego hará coincidir la contraseña de userDetailsService con la contraseña encontrada en LoginReq .
				* Este método generará una excepción si la autenticación no se realiza correctamente.
				*/
	         Authentication authentication =
	        		 //Aqui es donde valida si el usuario y contraseña enviados son correctos. Si no lo son da error
	                 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
	         		//Si pasamos de esta linea es porque nos hemos autenticado correctamente
	         System.out.println("Authentication: " + authentication);
	         SecurityContextHolder.getContext().setAuthentication(authentication);
	         
	         System.out.println("Autentication get context");
	         String token = jwtUtil.generateToken(authentication);
	         System.out.println("Token generado: " + token);
	         //String email = authentication.getName();
	         //User user = new User(email,"");
	         //String token = jwtUtil.createToken(user);
	         AuthenticationResponse loginRes = new AuthenticationResponse(token);
	         
	         System.out.println("Estamos validado el login: " + token);
	         return new ResponseEntity<AuthenticationResponse>(loginRes, HttpStatus.OK);
	         //return ResponseEntity.ok(loginRes);
	
	     }catch (BadCredentialsException e){
	    	 throw new BadRequestException("Log-002","Usuario o Password son invalidas");
	         //ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
	         //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	     }catch (Exception e){
	    	 throw new BadRequestException("Log-001","Usuario o Password son invalidas");
	         //ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
	         //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	     }
	}
}
