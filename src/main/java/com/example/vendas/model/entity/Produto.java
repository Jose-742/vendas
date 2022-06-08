package com.example.vendas.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable{
	

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Lob
	private byte[] imagem;
	
	@NotBlank(message = "{notblank.produto.descricao}") 
	private String descricao;
	
	@NotNull(message = "{notNull.produto.valor}") 
	private BigDecimal valor;
	
	@OneToMany(mappedBy="produto", cascade = CascadeType.ALL) 
	private List<ItemVenda> itemVendas;	

	@NotNull
	private boolean status;
	
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


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public List<ItemVenda> getItemVendas() {
		return itemVendas;
	}


	public void setItemVendas( List<ItemVenda> itemVendas) {
		this.itemVendas = itemVendas;
	}


	public byte[] getImagem() {
		return imagem;
	}


	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}


	public boolean getStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}	
}
