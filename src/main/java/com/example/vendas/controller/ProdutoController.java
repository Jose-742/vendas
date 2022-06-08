package com.example.vendas.controller;


import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.vendas.model.entity.Produto;
import com.example.vendas.service.ProdutoService;

@Transactional
@Controller
@RequestMapping("produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoRepository;
	
	@GetMapping("/form")
	public String form(Produto produto) {
		return "/produto/form";
	}


	@GetMapping("/list")//retorna 8 produtos inicio
	public ModelAndView list(ModelMap model) {		
		Sort sort = Sort.by(Sort.Direction.ASC, "descricao");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);					
		model.addAttribute("produtos", produtoRepository.listandoPorPaginacao(pageRequest));
		return new ModelAndView("/produto/list", model);
	}
	
	@GetMapping("/list/ajax")//retorna mais 8 produtos via ajax
	public String listAjax(@RequestParam(name = "page") int page, ModelMap model) {	
		Sort sort = Sort.by(Sort.Direction.ASC, "descricao");
		PageRequest pageRequest = PageRequest.of(page, 8, sort);			
		model.addAttribute("produtos", produtoRepository.listandoPorPaginacao(pageRequest));
		return "/produto/card";
	}
	
	 	
	@GetMapping("/busca")//busca produto pela descricao
	public String buscarProduto(@RequestParam("descricao") String descricao, ModelMap model) {
		if(descricao.isEmpty()) {
			return "redirect:/produtos/list";
		}	
		model.addAttribute("produtos", produtoRepository.findProdutosByDescricao(descricao));
		return "/produto/list";
	}
	

	@PostMapping("/save")//Salvar Produto
	public String save(@RequestParam("file") MultipartFile file, @Valid Produto produto,BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) { 
			return "/produto/form";
		}else if(file.getSize() == 0) {
			attr.addFlashAttribute("falha", "Imagem é Obrigatória.");
			return "redirect:/produtos/form";
		}
		try {
			produto.setImagem(file.getBytes());//convertendo em byte
		} catch (IOException e) {
			e.printStackTrace();
		}
		produto.setStatus(true);
		produtoRepository.save(produto);
		attr.addFlashAttribute("success", "Produto Cadastrado com sucesso.");
		return "redirect:/produtos/form";
	}
	
	 @GetMapping("/edit/{id}")//Editar Produto
	    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
	        model.addAttribute("produto", produtoRepository.produto(id)); 
	        return new ModelAndView("/produto/form", model);
	    }
	    
	    @PostMapping("/update")
	    public String update(@RequestParam("file") MultipartFile file, @Valid Produto produto, BindingResult result, RedirectAttributes attr) {	
		    if(result.hasErrors()) {
		    	return "/produto/form";
		    }else if(file.getSize() == 0) {
		    	produto.setImagem(produtoRepository.produto(produto.getId()).getImagem());		    	 
			}else {
				try {
					produto.setImagem(file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	    
		    produto.setStatus(true);
		    produtoRepository.update(produto);
	    	attr.addFlashAttribute("success", "Produto alterado com sucesso.");
		        return "redirect:/produtos/form";			
	    }
	    
	    @GetMapping("/remove/{id}")//Remover Produto
	    public ModelAndView remove(@PathVariable("id") Long id){
	    	produtoRepository.deletarProduto(id);
	        return new ModelAndView("redirect:/produtos/list");
	    }
	        
	    @GetMapping("/descricao")//autocomplete para o input de pesquisa do site
	   public ResponseEntity<?> autocompletePesquisa(@RequestParam("termo") String termo){
		   List<String> descricao = produtoRepository.findProdutosByTermo(termo);
		   return ResponseEntity.ok(descricao);
	   }
	    
	    @GetMapping("/descricao/detalhes/{id}")//pagina detalhes do produto
		public ModelAndView detalhesProdutos(@PathVariable("id") Long id, ModelMap model) {
			model.addAttribute("produto", produtoRepository.produto(id));	
			return new ModelAndView("/produto/descricao-produtos", model);
		}
	    
	    @GetMapping(value = "/imagem/{id}", produces= MediaType.IMAGE_JPEG_VALUE)//informando que retorna uma imagem
		@ResponseBody
		public byte[] exibirImagem(Model model, @PathVariable("id") Long id) {//lista a foto para tag img pois usando prod.imagem nao funciona !
			Produto produto = produtoRepository.produto(id);
			return produto.getImagem();
		}
}
