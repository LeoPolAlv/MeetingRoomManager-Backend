package com.eviden.meetingroom.security.Authorization;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.security.entity.UserAuth;

@Component
@Log4j2
//@PropertySource(value = {"classpath:application.properties"})
public class JwtUtil {

	@Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    
	public String generateToken(Authentication authentication) {
		
		Date issuedAt = new Date(System.currentTimeMillis()); //Fecha de emision que nos envia el sistema
        Date expiration = new Date( issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000) );
        
		System.out.println("Generando Token: " + authentication.getPrincipal());
		UserAuth usuarioMain = (UserAuth) authentication.getPrincipal();
		//System.out.println("UserAuth Mail: " + usuarioMain.getUsername());
		//logger.info("UsuarioMain: " + usuarioMain);
		Set<Rol> rolAux = usuarioMain.getRol();
		Set<String> rolName = new HashSet<String>();
		rolAux.forEach(rol -> {
			System.out.println("Rol: " + rol.getNombreRol());
			rolName.add(rol.getNombreRol());
		//	claims.put("rol",rol.getNombreRol());
		});
		
		Claims claims = Jwts.claims()
			             .setSubject(usuarioMain.getUsername());
		//claims.put("rol",usuarioMain.getRol());
		claims.put("roles",rolName);
	    
	    String jwts =  Jwts.builder()
				   .setSubject(usuarioMain.getUsername())
				   .setClaims(claims) //añadimos el rol del usuario al token
				   .setIssuedAt(issuedAt)
				   .setExpiration(expiration)
				   .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				   .compact();
		//System.out.println("Token generado antes de salir: " + jwts);
		return jwts;
		//return "Bearer " + jwts;
	}

	// subject --> Nombre del usuario
	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Token mal formado");
		} catch (UnsupportedJwtException e) {
			log.error("Token no soportado");
		} catch (ExpiredJwtException e) {
			log.error("Token expirado");
		} catch (IllegalArgumentException e) {
			log.error("Token vacio");
		} catch (SignatureException e) {
			log.error("Fallo con la firma");
			throw new SignatureException("Fallo con la firma");
		}
		return false;
	}
    
}
