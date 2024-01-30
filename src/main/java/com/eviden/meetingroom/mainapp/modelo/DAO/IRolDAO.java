package com.eviden.meetingroom.mainapp.modelo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.eviden.meetingroom.mainapp.modelo.entity.Rol;

public interface IRolDAO extends JpaRepository<Rol, Integer> {

	public Optional<Rol> findByNombreRol(String nombreRol);
}
