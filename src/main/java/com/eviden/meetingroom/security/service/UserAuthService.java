package com.eviden.meetingroom.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eviden.meetingroom.mainapp.modelo.DAO.IUsuarioDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Usuario;
import com.eviden.meetingroom.security.entity.UserAuth;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	/*
	* loadUserByUsername() obtiene el usuario por la dirección de correo electrónico de UserRepository , 
	* construye un objeto UserDetails a partir de él y luego regresa. 
	* Spring Security llamará internamente a este método con el correo electrónico proporcionado por el usuario 
	* y luego hará coincidir la contraseña del objeto UserDetails con la contraseña proporcionada por el usuario.
	*/
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDAO.findByEmail(username); //.orElseThrow(() -> new UsernameNotFoundException(username));
		System.out.println("Usuario service: " + usuario.getEmail());
		UserAuth userAuth = UserAuth.builder()
				                .username(usuario.getEmail())
				                .password(usuario.getPassword())
				                .rol(usuario.getRoles())
				                .build();
		if(userAuth == null) {
			System.out.println("UserAuth null");
			throw new UsernameNotFoundException(username);
		}
		System.out.println("UserAuth: " + userAuth.getRol());
		return userAuth;
	}

}
