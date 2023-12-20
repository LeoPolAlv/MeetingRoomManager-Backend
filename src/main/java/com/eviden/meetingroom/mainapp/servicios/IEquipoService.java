package com.eviden.meetingroom.mainapp.servicios;

import java.util.Optional;

import com.eviden.meetingroom.mainapp.modelo.entity.Equipo;

public interface IEquipoService {
	
	public Equipo generoEquipo(Equipo newEquipo);
	
	public void borroEquipo(Equipo equipo);
	
	public Optional<Equipo> buscoEquipoNombre(String nombre);
	
	public Optional<Equipo> buscoEqupoId(int id);

}
