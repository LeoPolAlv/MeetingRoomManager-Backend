package com.eviden.meetingroom.mainapp.modelo.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@OptimisticLocking(type = OptimisticLockType.VERSION) //El optimistic locking, o control de concurrencia optimista, es la gestión de concurrencia aplicado a sistemas transaccionales,
                                                        // para evitar dejar la Base de Datos inconsistente debido a accesos concurrentes a la Base de Datos. 
                                                        //Esta anotacion solo se usa si no hay @Version.
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueEmail", columnNames = { "email" })},
       indexes = { @Index(name = "email_inx", columnList = "email", unique = true) })
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long idUser;
	
	//@Column(unique = true)
	@NotBlank
	@Email
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)// Para no enviar este campo en la Response
	@NotBlank
	private String password;

	private boolean estadoUser;
	
	@OneToMany(mappedBy = "usuario")
	@JsonManagedReference(value = "usuario-reserva")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Reserva> reservas;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario-perfil", 
	           joinColumns = @JoinColumn(name = "FK_user"), 
	           inverseJoinColumns = @JoinColumn(name = "FK_rol"))
	private Set<Rol> roles;
	
	/*@ManyToOne(optional = true)
	@JoinColumn(name = "FK_oficina")
	@JsonBackReference(value = "oficina-usuario")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Oficina userOficina;
	*/
	
	/*@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "itemsmenu_usuario", 
	           joinColumns = @JoinColumn(name = "id_usuario"), 
	           inverseJoinColumns = @JoinColumn(name = "id_item")
	)
	private Set<ItemsMenu> items;*/
	
	/*
	 * JPA usa un campo de versión en sus entidades para detectar modificaciones simultáneas en el mismo registro del almacén de datos. 
	 * Cuando el tiempo de ejecución de JPA detecta un intento de modificar simultáneamente el mismo registro, 
	 * lanza una excepción OptimisticLockException a la transacción que intenta confirmar en último lugar.
	 */
	@Version
	@Column(name = "regVersion",columnDefinition = "bigint DEFAULT 0", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private long version;
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
