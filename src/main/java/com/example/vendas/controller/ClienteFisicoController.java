package com.example.vendas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.vendas.model.entity.CartoesCredito;
import com.example.vendas.model.entity.ClientePF;
import com.example.vendas.model.entity.Enderecos;
import com.example.vendas.model.entity.Perfil;
import com.example.vendas.model.entity.Usuario;
import com.example.vendas.model.repository.CartoesCreditoRepository;
import com.example.vendas.model.repository.EnderecosRepository;
import com.example.vendas.service.ClientePFService;
import com.example.vendas.service.PerfilService;
import com.example.vendas.service.UsuarioService;

@Transactional
@Controller
@RequestMapping("fisicas")
public class ClienteFisicoController {

	@Autowired
	private ClientePFService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PerfilService perfilRepository;
	
	@Autowired
	private EnderecosRepository enderecoRepository;
	
	@Autowired
	private CartoesCreditoRepository cartoesRepository;
	
	@GetMapping("/form")//formulario de cadastro
	public String form(ClientePF clientePF) {
		return"/fisica/cadastro";
	}
		
	
	@GetMapping("/list")//listando todos os usuario
	public ModelAndView list(ModelMap model) {
		model.addAttribute("clientesFisicos", service.clientesFisicos());
		return new ModelAndView("/fisica/list", model);
	}
	
	@PostMapping("/save")//Salvar Usuario
	public String save( @Valid ClientePF clientePF, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return "/fisica/cadastro";
		}
		if(clientePF.validandoUsuarioSennha() ) {
			String msg = clientePF.validSenha() ? "Senha é Obrigatorio" : "Email é Obrigatorio";
			result.addError(new ObjectError("fail", msg));
			return "/fisica/cadastro";
		}
		if(clienteExiste(clientePF)) {
			result.addError(new ObjectError("fail", "Usuário já existe no sistema!"));
			return "/fisica/cadastro";
		}		
		List<Perfil> perfis = new ArrayList<>();
		perfis.add(perfilRepository.perfil(2L));//2LPadrao User				
		Usuario usuario = clientePF.getUsuario();
		usuario.setPerfil(perfis);//USER
		usuarioService.salvarUsuario(usuario);
		service.save(clientePF);		
		attr.addFlashAttribute("success", "Cadastrado com sucesso.");
		return "redirect:/fisicas/form";
	}

	@GetMapping("/edit/Cliente")//Editar usuario no formulario
    public ModelAndView edit(ModelMap model ) {
		ClientePF clientePF = addCliente();	
		model.addAttribute("clientePF", clientePF);
        return new ModelAndView("/fisica/cadastro", model);
    }
		
    @PostMapping("/update")//Atualizar usuario 
    public String update( @Valid ClientePF clientePF, BindingResult result, RedirectAttributes attr) {	
    	if(result.hasErrors()) {
			return "/fisica/cadastro";
		}
		if(clientePF.validandoUsuarioSennha() ) {
			String msg = clientePF.validSenha() ? "Senha é Obrigatorio" : "Email é Obrigatorio";
			result.addError(new ObjectError("fail", msg));
			return "/fisica/cadastro";
		}
		
		List<Perfil> perfis = new ArrayList<>();
		perfis.add(perfilRepository.perfil(2L));		
		Usuario usuario = clientePF.getUsuario();
		usuario.setPerfil(perfis);		
		usuarioService.AtualizarUsuario(usuario);
		service.update(clientePF);		
    	attr.addFlashAttribute("success", "Cliente Físico atualizado com sucesso.");
	        return "redirect:/fisicas/form";		
    }
    
    @GetMapping("/adresses")//listando endereço
	public ModelAndView listEndereco(Enderecos enderecos, ModelMap model) {
		ClientePF cliente = addCliente();	
		model.addAttribute("enderecos", enderecoRepository.findByBuscarPorCliente(cliente.getId()));
		return new ModelAndView("/fisica/enderecos", model);
	}
	
	@PostMapping("/adresses/save")//salvando endereço
	public String saveAdresses(@Valid Enderecos enderecos, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {			
			return "/fisica/enderecos";
		}
		ClientePF cliente = addCliente();
		enderecos.setStatus(true);
		enderecos.setClientePF(cliente);
		enderecoRepository.save(enderecos);		
		cliente.addEndereco(enderecos);	
		service.update(cliente);		
		attr.addFlashAttribute("sucesso", "Cadastrado com sucesso.");
		return "redirect:/fisicas/adresses";
	}
	
	@GetMapping("/adresses/remove/{id}")//Removendo Endereço 
	public ModelAndView removeEndereco(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
		Enderecos endereco = enderecoRepository.findById(id).get();
		endereco.setStatus(false);
		attr.addFlashAttribute("sucesso", "Excluído com sucesso.");
		return new ModelAndView("redirect:/fisicas/adresses");
	}
    
	@PostMapping("/cartao/save")//Salvar Cartao de Credito
    public String saveCartaoCredito(@Valid CartoesCredito cartao, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute("falha", "Ops... Algo deu errado no seu cadastro");
			return "redirect:/vendas/pagamento";
		}
		System.out.println(cartao.getNumeroCartao());
		ClientePF cliente = addCliente();
		cartao.setCliente(cliente);
		cartoesRepository.save(cartao);		
		cliente.addCartao(cartao);
		service.update(cliente);		
		attr.addFlashAttribute("sucesso", "Cartão cadastrado.");
		return "redirect:/vendas/pagamento";
    }
	
	@GetMapping("/cartao/remove/{id}")//Removendo cartao de credito
	public String removerCartaoCredito(@PathVariable("id") Long id) {
		CartoesCredito cartao = cartoesRepository.findById(id).get();
		cartoesRepository.delete(cartao);
		return "redirect:/vendas/pagamento";
	}
	
    @PostMapping("/buscar/nome")//Buscar usuario por nome
    public ModelAndView buscarPorNome(@RequestParam(name = "nome", required = false) String nome, ModelMap model) {
    	model.addAttribute("clientesFisicos", service.buscarPorNome(nome));
    	return new ModelAndView("/fisica/list", model);
    }
    
 
  	private  ClientePF addCliente() { //Metodo para retornar cliente logado no sistema
  		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();// retorna um objeto Authentication
  		String email = null;
  			if (principal instanceof UserDetails) {//se principal for uma instância de UserDetails
  				email = ((UserDetails)principal).getUsername(); //retorna o email do usuario
  				Usuario usuario = usuarioService.buscarPorEmail(email);
  				for(ClientePF cliente : service.clientesFisicos()) {
  					if(cliente.getUsuario().getId() == usuario.getId()) {
  						return cliente;
  					}
  				}
  			}
			return null;		
  	}
    
   private boolean clienteExiste(ClientePF clientePF) { //verificar se o cliente ja existe no sistema
    	Usuario user = usuarioService.buscarPorEmail(clientePF.getUsuario().getEmail());
    	if(user == null) {
    		return false;   		
    	}
    	return true;
    }
}
