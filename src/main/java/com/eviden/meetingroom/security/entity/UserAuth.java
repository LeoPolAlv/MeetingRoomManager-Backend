package com.eviden.meetingroom.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eviden.meetingroom.mainapp.modelo.entity.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Entity
//@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserAuth implements UserDetails{
	
	private String username;
	private String password;
	private Set<Rol> rol;
	
	// Convertimos la clase Rol a la clase GrantedAuthority
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		rol.forEach( rol -> {
			authorities.add(new SimpleGrantedAuthority(rol.getNombreRol()));
		});
		
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
