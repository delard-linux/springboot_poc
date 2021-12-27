package com.drd.springbootpoc.jwt.app.model.entity.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles_usuario", uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id", "role_code"})})
public class RolUsuario implements Serializable {

	private static final long serialVersionUID = -4331019072127918780L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "user_id")
	private Long userId;

	@NotEmpty
	@NotNull
	@NotBlank
	@Column(name = "role_code")
	private String rolUsuarioCode;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRolUsuarioCode() {
		return rolUsuarioCode;
	}

	public void setRolUsuarioCode(String rolUsuarioCode) {
		this.rolUsuarioCode = rolUsuarioCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
