package com.example.vendas.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("session")
@Component
@Entity
@Table(name = "tb_venda")
public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_compra", columnDefinition = "DATE")
	private LocalDate data;

	
	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
	private List<ItemVenda> itemVendas;
	
	@ManyToOne
	@JoinColumn(name="id_cliente_fisico_fk")
	private ClientePF clientePF;
	
	@NotBlank(message = "A forma de pagamento é Obrigatória.")
	private String formaPagamento;
	
	@OneToOne
	private Enderecos enderecoEntrega;

	public Venda() {
		this.data = LocalDate.now();
		this.itemVendas = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<ItemVenda> getItemVendas() {
		return itemVendas;
	}

	public void setItemVendas(List<ItemVenda> itens) {
		this.itemVendas = itens;
	}
	
	public ClientePF getClientePF() {
		return clientePF;
	}

	public void setClientePF(ClientePF clientePF) {
		this.clientePF = clientePF;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public Enderecos getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Enderecos enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public double total() {
		
		double total = 0;
		for(ItemVenda item :  itemVendas) {
			total += item.total();
		}
		return total;
	}
}
