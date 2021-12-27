package com.drd.springbootpoc.jwt.app.model.entity.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 4441666990521895163L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@NotNull
	@NotBlank
	@Column(name = "username",length = 30, unique = true)
	private String username;

	@NotEmpty
	@NotNull
	@NotBlank
	@Column(name = "password",length = 60)
	private String password;

	@NotNull
	private Boolean enabled;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<RolUsuario> rolesUsuario;

	public Usuario() {
		this.rolesUsuario = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<RolUsuario> getRolesUsuario() {
		return this.rolesUsuario;
	}

	public void setRolesUsuario(List<RolUsuario> rolesUsuario) {
		this.rolesUsuario = rolesUsuario;
	}
	
	public void addRolesUsuario(RolUsuario rolUsuario) {
		this.rolesUsuario.add(rolUsuario);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
