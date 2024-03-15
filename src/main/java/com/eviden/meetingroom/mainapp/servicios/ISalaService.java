package com.eviden.meetingroom.mainapp.servicios;

import java.util.List;
import java.util.Optional;

import com.eviden.meetingroom.mainapp.modelo.entity.Sala;

public interface ISalaService {
	
	public Sala generoSala(Sala newSala);
	
	public void borroSala(Sala sala);
	
	public Optional<Sala> buscoSalaNombre(String nombre);
	
	public Optional<Sala> buscoSalaId(int id);
	
	public Optional<List<Sala>>buscoAllSalas();

}
