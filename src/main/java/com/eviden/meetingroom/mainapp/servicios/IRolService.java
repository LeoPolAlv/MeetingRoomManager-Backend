package com.eviden.meetingroom.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.meetingroom.mainapp.modelo.entity.Rol;

public interface IRolService {
	
	public Rol generoRol(Rol newRol);
	
	public void borroRol (Rol rol);
	
	public Optional<Rol> buscoRol(int idRol);
	
	public Optional<Rol> buscoRolNombre( String nombreRol);
	
	public Optional<List<Rol>> buscoRoles();

}
