package com.eviden.meetingroom.mainapp.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eviden.meetingroom.mainapp.modelo.DAO.IUsuarioDAO;
import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.mainapp.modelo.entity.Usuario;
import com.eviden.meetingroom.mainapp.servicios.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	IUsuarioDAO usuarioDao;
	
	@Override
	@Transactional
	public Usuario newUser(Usuario newUsuario) {
		
		return usuarioDao.save(newUsuario);
	}

	@Override
	@Transactional
	public void deleteUser(Usuario delUser) {
		// TODO Auto-generated method stub
		usuarioDao.delete(delUser);
	}

	@Override
	@Transactional(readOnly = true)
	//@OrderBy("name ASC")
	public Optional<List<Usuario>> allUsuarios() {

		return usuarioDao.findAllByOrderByEmailAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> buscoPorEmail(String email) {

		return Optional.ofNullable(usuarioDao.findByEmail(email));
	}

	@Override
	@Transactional
	public void newRol(Rol rol,String email) {
		usuarioDao.insertRol(rol, email);
	}

	@Override
	@Transactional
	public Optional<List<Usuario>> usuariosActivos(boolean estado) {
		
		return usuarioDao.findByEstadoUser(estado);
	}	
}
