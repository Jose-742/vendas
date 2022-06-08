package com.example.vendas.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class CartoesCredito implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{notNull.cartoescredito.numerocartao}")
	private Long numeroCartao;
	
	@NotBlank(message = "{notBlank.cartoescredito.nomecartao}")
	private String nomeCartao;
	
	@DateTimeFormat(iso = ISO.DATE)//"dd/MM/yyyy"
	private LocalDate dataExpiracao;
	
	@NotNull(message = "{notNull.cartoescredito.codigoseguranca}")
	private int codigoSeguranca;
	
	@ManyToOne
	@JoinColumn(name="id_clientePF_fk")
	private ClientePF clientePF;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(Long numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public LocalDate getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(LocalDate dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public int getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(int codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public ClientePF getCliente() {
		return clientePF;
	}

	public void setCliente(ClientePF cliente) {
		this.clientePF = cliente;
	}
}
