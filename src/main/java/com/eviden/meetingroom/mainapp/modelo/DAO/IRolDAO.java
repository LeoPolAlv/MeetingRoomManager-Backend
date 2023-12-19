package com.eviden.meetingroom.mainapp.modelo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.meetingroom.mainapp.modelo.entity.Rol;

public interface IRolDAO extends JpaRepository<Rol, Integer> {

}
