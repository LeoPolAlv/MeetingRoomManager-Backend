package com.eviden.meetingroom.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.meetingroom.mainapp.modelo.entity.Usuario;

public interface IUserRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String username);
}
