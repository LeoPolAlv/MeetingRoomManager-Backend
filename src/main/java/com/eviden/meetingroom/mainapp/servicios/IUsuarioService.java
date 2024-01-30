package com.eviden.meetingroom.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.mainapp.modelo.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario newUser(Usuario newUsuario);
	
	public void deleteUser(Usuario delUSer);
	
	public Optional<List<Usuario>> allUsuarios();

	public Optional<Usuario> buscoPorEmail(String email);
	
	public void newRol(Rol rol, String email);
	
	public Optional<List<Usuario>> usuariosActivos(boolean estado);
}
