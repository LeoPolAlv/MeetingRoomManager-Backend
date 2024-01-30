package com.eviden.meetingroom.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.meetingroom.mainapp.modelo.DAO.IEquipoDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Equipo;
import com.eviden.meetingroom.mainapp.servicios.IEquipoService;


@Service
public class EquipoServiceImpl implements IEquipoService {

	@Autowired
	IEquipoDAO equipoDao;
	
	@Override
	@Transactional
	public Equipo generoEquipo(Equipo newEquipo) {
		
		return equipoDao.save(newEquipo);
	}

	@Override
	@Transactional
	public void borroEquipo(Equipo equipo) {
		
		equipoDao.delete(equipo);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Equipo> buscoEquipoNombre(String nombre) {
		
		return equipoDao.findByNombreEquipo(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Equipo> buscoEqupoId(int id) {
		
		return equipoDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<List<Equipo>> buscoAllEquipos() {
	
		return Optional.ofNullable(equipoDao.findAll());
	}

}
