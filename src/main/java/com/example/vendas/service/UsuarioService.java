package com.example.vendas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vendas.model.entity.Usuario;
import com.example.vendas.model.repository.UsuarioRepository;

@Transactional
@Repository
public class UsuarioService implements UserDetailsService{

	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario buscarPorEmail(String email) {		
		return repository.findByEmail(email);
	}
	
	@Override 
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {//autenticando usuario
		Usuario usuario = buscarPorEmail(email);
		return new User(usuario.getEmail(), usuario.getSenha(),true,true,true,true,usuario.getAuthorities());
	}
	
	public void salvarUsuario(Usuario usuario) {//SALVAR USUARIO
		repository.save(cryptSenha(usuario));
	}
	
	public void AtualizarUsuario(Usuario usuario) {//ALTERAR USUARIO
		repository.save(cryptSenha(usuario));
	}
		
	private Usuario cryptSenha(Usuario usuario) {//CRYPTOGRAFAR SENHA
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		return usuario;
	}
}
