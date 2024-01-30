package com.eviden.meetingroom.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.meetingroom.mainapp.modelo.DAO.IRolDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.mainapp.servicios.IRolService;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	IRolDAO rolDAO;
	
	@Override
	@Transactional
	public Rol generoRol(Rol newRol) {
		
		return rolDAO.save(newRol);
	}

	@Override
	@Transactional
	public void borroRol(Rol rol) {
		rolDAO.delete(rol);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Rol> buscoRol(int idRol) {
		
		return rolDAO.findById(idRol);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<List<Rol>> buscoRoles() {
		
		return Optional.ofNullable(rolDAO.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Rol> buscoRolNombre(String nombreRol) {
		
		return rolDAO.findByNombreRol(nombreRol);
	}
}
