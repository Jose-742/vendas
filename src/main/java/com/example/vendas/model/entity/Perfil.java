package com.example.vendas.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name = "perfis")
public class Perfil  implements GrantedAuthority, Serializable{//GrantedAuthority
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;

	@ManyToMany(mappedBy = "perfil")
	private List<Usuario> usuarios;
	
	@Override
	public String getAuthority() {//metodo da classe GrantedAuthority
		return descricao;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
