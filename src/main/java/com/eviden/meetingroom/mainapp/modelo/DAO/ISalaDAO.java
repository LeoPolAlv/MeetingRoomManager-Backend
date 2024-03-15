package com.eviden.meetingroom.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.meetingroom.mainapp.modelo.entity.Sala;

public interface ISalaDAO extends JpaRepository<Sala, Integer> {

	//public Optional<Sala> findByIdSala();
	
	public Optional<Sala> findByNombreSala(String nombreSala);
	
	
}
