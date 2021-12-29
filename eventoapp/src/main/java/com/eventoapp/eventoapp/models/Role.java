package com.eventoapp.eventoapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

	@Id
	private String nomeRole;
	
	@ManyToMany
	private List<Usuario> usuario;
	
	public String getNomeRole() {
		return nomeRole;
	}



	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}



	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nomeRole;
	}

}
