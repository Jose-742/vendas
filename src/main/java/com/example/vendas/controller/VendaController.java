package com.example.vendas.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.vendas.model.entity.ClientePF;
import com.example.vendas.model.entity.Enderecos;
import com.example.vendas.model.entity.ItemVenda;
import com.example.vendas.model.entity.Produto;
import com.example.vendas.model.entity.Venda;
import com.example.vendas.model.repository.EnderecosRepository;
import com.example.vendas.model.repository.UsuarioRepository;
import com.example.vendas.service.ClientePFService;
import com.example.vendas.service.ProdutoService;
import com.example.vendas.service.VendaService;


@Scope("request")
@Transactional
@Controller
@RequestMapping("vendas")
public class VendaController {
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private ClientePFService clienteService;
		
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private EnderecosRepository enderecoRepository;
	
	@Autowired
	Venda venda;
		
	@GetMapping("/list")//Todas As Vendas
	public ModelAndView list(ModelMap model) {
		model.addAttribute("vendas", vendaService.vendas());
		return new ModelAndView("/venda/list", model);
	}
	
	@GetMapping("/compras/list")//Compras do Usuario
	public ModelAndView comprasList(ModelMap model) {
		addCliente();
		model.addAttribute("vendas", vendaService.buscarVendasUsuario(venda.getClientePF().getId()));
		return new ModelAndView("/venda/list", model);
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("venda", vendaService.venda(id));
		return new ModelAndView("/venda/detail", model);
	}
	
	
	private  void addCliente() {//Metodo para Adicionar Cliente Na Compra
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();// retorna um objeto Authentication
		String email = null;
			if (principal instanceof UserDetails) {//se principal for uma instância de UserDetails
				email = ((UserDetails)principal).getUsername(); //retorna o email do usuario				
				for(ClientePF cliente : clienteService.clientesFisicos()) {
					if(cliente.getUsuario().getEmail().equals(email)) {
						venda.setClientePF(cliente); //add Cliente na Venda
					}
				}				
			}		
	}
	
	@GetMapping("/save")//Salvando Venda e limpa a lista 
	public String salvar(RedirectAttributes attr) {
		if(venda.getItemVendas().size() == 0 ) {
			attr.addFlashAttribute("falha", "Carrinho está Vazio");
			return "redirect:/vendas/carrinho";
		}
		venda.setId(null);
		addCliente(); //adicionando cliente na venda		
		vendaService.save(venda);
		venda.getItemVendas().clear();	
		attr.addFlashAttribute("sucesso",  "Parabéns sua Compra Acaba de ser Concluída com Sucesso.");
		return "redirect:/vendas/carrinho";
	}
	
	@PostMapping("/add")
	public ModelAndView addItemCarrinho(ItemVenda item) {//Metodo Adiciona produto no carrinho
		Produto p = produtoService.produto(item.getProduto().getId());
		boolean controle = true;
		for(ItemVenda itemV : venda.getItemVendas()) {//for para add quantidade caso o produto ja exista no carrinho
			if(itemV.getProduto().getId() == p.getId()) {
				itemV.setQuantidade( itemV.getQuantidade() +1);
				controle = false;
			}
		}
		if(controle) {
			item.setProduto(p);
			item.setValorAbsoluto(p.getValor());//VALOR ABSOLUDO DA VENDA
			item.setQuantidade(1L);
			item.setVenda(venda);
			venda.getItemVendas().add(item);
		}
		return new ModelAndView("redirect:/produtos/list");
	}
		
	@GetMapping("/carrinho")//pagina carrinho
	public String carrinho(){	
		return "/carrinho/cart";
	}
	
	@GetMapping("/pagamento")//pagina pagamento
	public ModelAndView pagamento(ModelMap model){	
		addCliente();
		model.addAttribute("cartoes", venda.getClientePF().getCartoes());
		return new ModelAndView("/carrinho/pagamento", model);
	}
	
	@GetMapping("/pagamento/save")//Metodo para Salvar a forma de pagamento
	public ResponseEntity<?> pagamentoSave(@RequestParam("forma") String formaPagamento) {
		venda.setFormaPagamento(formaPagamento);
		return ResponseEntity.ok("sucesso");
	}
	
	@GetMapping("/finalizar/pedido")//finalizar pedido 
	public ModelAndView finalizarPagamento(ModelMap model) {
		addCliente();
		model.addAttribute("enderecos", enderecoRepository.findByBuscarPorCliente(venda.getClientePF().getId()));
		model.addAttribute("venda", venda);
		return new ModelAndView("/carrinho/finalizarPedido", model);
	}
	
	@GetMapping("/add/endereco/entrega")//adicionar endereço de entrega
	public ResponseEntity<?> addEnderecoEntrega(@RequestParam("id") Long id){
		Enderecos endereco = enderecoRepository.findById(id).get();
		endereco.setStatus(true);
		venda.setEnderecoEntrega(endereco);
		return ResponseEntity.ok("sucesso");
	}
		
	@GetMapping("/alterarQuantidade/{index}/{acao}")//Alterar quantidade do produto
	public ModelAndView aleterarQuantidade(@PathVariable int index, @PathVariable Integer acao) {//Metodo altera a Quantidade do produto
		Long variavel = venda.getItemVendas().get(index).getQuantidade();
		if(acao == 1) {
			venda.getItemVendas().get(index).setQuantidade(variavel + 1);
		}else if(variavel > 1){
			venda.getItemVendas().get(index).setQuantidade(variavel - 1);
		}
		return new ModelAndView("redirect:/vendas/carrinho");
	}
	
	@GetMapping("/remover/{index}")//Metodo que remove item do Carrinho
	public ModelAndView removerProdutoSessao(@PathVariable("index") int index) {
		venda.getItemVendas().remove(index); //remove o item da minha lista
		return new ModelAndView("redirect:/vendas/carrinho");
	}	
	
	@PostMapping("/buscar/data")//buscar por data ADMIN
	public ModelAndView buscarPorData(@RequestParam(name="data", required = false )
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrada, ModelMap model ) {		
		model.addAttribute("vendas", vendaService.buscarPorData(dataEntrada));
		return new ModelAndView("/venda/list", model);
	}
	
	@GetMapping("/buscar/user/data")//buscar por data USER
	public ModelAndView buscarPorDataUsuario(@RequestParam(name="data", required = false )
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrada, ModelMap model ) {	
		addCliente();
		model.addAttribute("vendas", vendaService.buscarPorDataUser(dataEntrada, venda.getClientePF().getId()));
		return new ModelAndView("/venda/list", model);
	}
}
