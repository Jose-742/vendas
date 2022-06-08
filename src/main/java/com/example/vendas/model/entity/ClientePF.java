package com.example.vendas.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Transactional
@Entity
@Table(name = "tb_cliente_fisico")
public class ClientePF extends Cliente{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{notblank.clientepf.cpf}") //(message = "O CPF é obrigatório.") 
	@Length(message = "{length.clientepf.cpf}", max=14, min=14) //(message = "São 11 números", max=14, min=14)
	private String cpf;
	
	@OneToOne(cascade= CascadeType.ALL)//(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "clientePF")//remove toda venda que estiver relacionado com clientePF cascade = CascadeType.ALL, orphanRemoval = true
	private List<Venda> vendas;
	
	@OneToMany(mappedBy = "clientePF") 
	private List<Enderecos> enderecos;
	
	@OneToMany( mappedBy = "clientePF") 
	private List<CartoesCredito> cartoes;
	
	public ClientePF() {
		super();
	}
	
	public ClientePF(Long id, String nome, String cpf) {
		super(id, nome);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Enderecos> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Enderecos> enderecos) {
		this.enderecos = enderecos;
	}

	public List<CartoesCredito> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<CartoesCredito> cartoes) {
		this.cartoes = cartoes;
	}

	public boolean validSenha() {
		if(this.usuario.getSenha().isEmpty()) {
			return true;
		}
		return false;
	}
	
	public void addEndereco(Enderecos endereco) {
		enderecos.add(endereco);
	}
	
	public void addCartao(CartoesCredito cartao) {
		cartoes.add(cartao);
	}
	
	public boolean validandoUsuarioSennha() {
		if(this.usuario.getEmail().isEmpty() || this.usuario.getSenha().isEmpty()) {
			return true;
		}
		return false;
	}
	
}
