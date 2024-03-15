package com.eviden.meetingroom.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eviden.meetingroom.mainapp.modelo.DAO.ISalaDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Sala;
import com.eviden.meetingroom.mainapp.servicios.ISalaService;

import jakarta.transaction.Transactional;

@Service
public class SalaServiceImpl implements ISalaService {

	@Autowired
	ISalaDAO salaDao;
	
	@Override
	@Transactional
	public Sala generoSala(Sala newSala) {
		return salaDao.save(newSala);
	}

	@Override
	public void borroSala(Sala sala) {
		salaDao.delete(sala);
	}

	@Override
	public Optional<Sala> buscoSalaNombre(String nombre) {
		return salaDao.findByNombreSala(nombre);
	}

	@Override
	public Optional<Sala> buscoSalaId(int id) {
		return salaDao.findById(id);
	}

	@Override
	public Optional<List<Sala>> buscoAllSalas() {
		return Optional.ofNullable(salaDao.findAll());
	}

}
