package com.eviden.meetingroom.mainapp.servicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eviden.meetingroom.mainapp.modelo.DAO.IEquipoDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Equipo;
import com.eviden.meetingroom.mainapp.servicios.IEquipoService;

@Service
public class EquipoServiceImpl implements IEquipoService {

	@Autowired
	IEquipoDAO equipoDao;
	
	@Override
	public Equipo generoEquipo(Equipo newEquipo) {
		
		return equipoDao.save(newEquipo);
	}

	@Override
	public void borroEquipo(Equipo equipo) {
		
		equipoDao.delete(equipo);
	}

	@Override
	public Optional<Equipo> buscoEquipoNombre(String nombre) {
		
		return equipoDao.findByNombreEquipo(nombre);
	}

	@Override
	public Optional<Equipo> buscoEqupoId(int id) {
		
		return equipoDao.findById(id);
	}

}
