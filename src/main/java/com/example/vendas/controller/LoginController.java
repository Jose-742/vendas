package com.example.vendas.controller;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class LoginController {

	
	@GetMapping("/login")
	public String login() {//ClientePF cliente
		return "/autenticacao/login";
	}
}
