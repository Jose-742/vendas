package com.example.vendas.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tb_itemvenda")
public class ItemVenda implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long quantidade;
	
	@ManyToOne
	@JoinColumn(name="id_produto_fk")
	private Produto produto; 
	
	@ManyToOne
	@JoinColumn(name="id_venda_fk")
	private Venda venda;
	
	@NotNull
	private BigDecimal valorAbsoluto;
	
	
	public ItemVenda() {}

	public ItemVenda(Long id, Long quantidade, Venda venda) {
		this.id = id;
		this.quantidade = quantidade;
		this.venda = venda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	public BigDecimal getValorAbsoluto() {
		return valorAbsoluto;
	}

	public void setValorAbsoluto(BigDecimal valorAbsoluto) {
		this.valorAbsoluto = valorAbsoluto;
	}

	public List<Long> stringIds(String text) {
		String [] textoSeparado = text.split(",");
		List<Long> idsLong = new ArrayList<>();
		for(String id : textoSeparado) {
			idsLong.add(Long.parseLong(id));
		}
		return idsLong;
	}

	public Double total() {
		return valorAbsoluto.doubleValue() * quantidade;
	}
}
