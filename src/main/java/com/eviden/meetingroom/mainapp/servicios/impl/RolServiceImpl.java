package com.eviden.meetingroom.mainapp.servicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eviden.meetingroom.mainapp.modelo.DAO.IRolDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.mainapp.servicios.IRolService;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	IRolDAO rolDAO;
	
	public Rol generoRol(Rol newRol) {
		
		return rolDAO.save(newRol);
	}

	@Override
	public void borroRol(Rol rol) {
		rolDAO.delete(rol);
	}

	@Override
	public Optional<Rol> buscoRol(int idRol) {
		
		return rolDAO.findById(idRol);
	}
	
	

}
