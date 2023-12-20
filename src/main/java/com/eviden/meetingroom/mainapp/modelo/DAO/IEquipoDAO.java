package com.eviden.meetingroom.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.meetingroom.mainapp.modelo.entity.Equipo;

public interface IEquipoDAO extends JpaRepository<Equipo, Integer>{

	public Optional<Equipo> findByNombreEquipo(String nombre);
}
