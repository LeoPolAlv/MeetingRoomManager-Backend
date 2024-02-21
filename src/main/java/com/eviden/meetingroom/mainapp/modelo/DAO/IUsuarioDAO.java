package com.eviden.meetingroom.mainapp.modelo.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eviden.meetingroom.mainapp.modelo.entity.Rol;
import com.eviden.meetingroom.mainapp.modelo.entity.Usuario;

@Repository
public interface IUsuarioDAO extends JpaRepository<Usuario, Long>{
	
	public Usuario findByEmail(String email);
	
	@Modifying
	@Query("UPDATE Usuario u SET u.roles = ?1 WHERE u.email = ?2")
	public void insertRol(Rol rol, String email);

	/*@Query("SELECT u.* FROM usuario u WHERE u.List<Usuario> findByEstadoUser(boolean estadoUser);")
	
	public List<Usuario> findUserActivos();*/
	
	public Optional<List<Usuario>> findByEstadoUser(boolean estadoUser);
	
	public Optional<List<Usuario>> findAllByOrderByEmailAsc();
}
