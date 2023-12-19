package com.eviden.meetingroom.mainapp.modelo.entity;

import lombok.Getter;

@Getter
public enum EstadoReserva {

	PENDIENTE(0),CONFIRMADA(1),CANCELADA(2);
	
	public int idEstado;
	
	private EstadoReserva(int idEstado) {
		this.idEstado = idEstado;
	}
	
}
