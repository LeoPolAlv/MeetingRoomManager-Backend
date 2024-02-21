package com.eviden.meetingroom.security.Authorization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eviden.meetingroom.exceptions.exception.BadRequestException;
import com.eviden.meetingroom.security.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
*  Se llamará a este método para cada solicitud de nuestra aplicación. Este método lee el token de portador de los encabezados de las solicitudes 
* y resuelve las reclamaciones.
*  Primero, verifica si hay algún token de acceso presente en el encabezado de la solicitud. Si el accessToken es nulo. 
* Pasará la solicitud a la siguiente cadena de filtros.
*  Cualquier solicitud de inicio de sesión no tendrá el token jwt en su encabezado, por lo tanto, pasará a la siguiente cadena de filtros.
*  Si hay algún acessToken presente, validará el token y luego autenticará la solicitud en SecurityContext .
*/

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserAuthService userAuthService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Map<String, Object> errorDetails = new HashMap<>();
		
		// TODO Auto-generated method stub
		try {
			//Obtenemos el token que nos llega en la cabecera de la peticion.
			String token = getToken(request);
			System.out.println("JwtAuthorizationFilter " + token);
			//Validamos el token recibido.
			if (token != null && jwtUtil.validateToken(token)) {
				System.out.println("Entro por Token ");
				String nombreUsuario = jwtUtil.getNombreUsuarioFromToken(token);
				//logger.info("Nombre Usuario: " + nombreUsuario);
				UserDetails userDetails = userAuthService.loadUserByUsername(nombreUsuario);
				//logger.info("UserDetails: " + userDetails);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				//Insertaos en el contexto de spring security el usuario que ha sido validado.
				SecurityContextHolder.getContext().setAuthentication(auth);
				// filterChain.doFilter(req, res);
			} else {
				System.out.println("Entro por login porque Token no tengo");
				filterChain.doFilter(request, response);
				return;
			}
		} catch (Exception e) {
			//throw new BadRequestException("Log-003","Error en la autenticacion del usuario");
			//errorDetails.put("fecha", e.getStackTrace());
			errorDetails.put("mensaje", "Error en la autenticacion del usuario");
	        errorDetails.put("code", "LOG-003");
	        response.setStatus(HttpStatus.FORBIDDEN.value());
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

	        mapper.writeValue(response.getWriter(), errorDetails);
		}
		filterChain.doFilter(request, response);
	}
	
	// Metodo para obtener el token, sin el prefijo 'Bearer'
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		//logger.error("header: " + header);
		// Si no es nulo y comienza por el prefijo 'Bearer'
		if (header != null && header.startsWith("Bearer"))
			return header.replace("Bearer ", "");

		return null;
	}

}
