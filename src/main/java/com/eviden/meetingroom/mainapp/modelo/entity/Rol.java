package com.eviden.meetingroom.mainapp.modelo.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(columnList = "nombreRol", name = "rol_idx", unique = true)})
public class Rol implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRol;

	@Column(nullable = false)
	@NotBlank
	private String nombreRol;
	
	private String descripcionRol; 
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<Usuario> usuarios;
	
	@Version
	@Column(name = "regVersion", columnDefinition = "bigint DEFAULT 0", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long version;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
