package com.eviden.meetingroom.mainapp.modelo.entity;

import lombok.Getter;

@Getter
public enum EstadoSala {
   DISPONIBLE(0), NODISPONIBLE(1);

   int idEstado;

   private EstadoSala(int idEstado) {
       this.idEstado = idEstado;
   }
/*
   public int getIdEstado() {
       return idEstado;
   }
   */
}
