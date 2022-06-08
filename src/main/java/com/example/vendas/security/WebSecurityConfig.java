package com.example.vendas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.vendas.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService service;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.//.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
                authorizeRequests() //define com as requisiÃ§Ãµes HTTP devem ser tratadas com relaÃ§Ã£o Ã  seguranÃ§a.
                
                //todos tem acesso sem ter que efetuar login
                .antMatchers("/style/**","/script/**","/img/**").permitAll()
                .antMatchers("/", "/home", "https://**").permitAll()
                .antMatchers("/produtos/list", "/produtos/descricao/**", "/produtos/busca/**", "/produtos/list/ajax/**","/produtos/imagem/**").permitAll()
                .antMatchers("/fisicas/form", "/fisicas/save").permitAll()
                .antMatchers("/vendas/add", "/vendas/carrinho", "/vendas/remover/**", "/vendas/alterarQuantidade/**/**").permitAll()
                
                //somente o administrador tem acesso
                .antMatchers("/fisicas/list").hasRole("ADMIN") 
                .antMatchers("/produtos/form", "/produtos/edit/*", "/produtos/remove/*").hasRole("ADMIN")
                .antMatchers("/vendas/list").hasRole("ADMIN")///compras/list
                
                //quem estiver autenticado
                .antMatchers("/vendas/details/*").authenticated()
                
                //somente o usuario tem acesso
                .antMatchers("/vendas/compras/list*", "/vendas/buscar/user/data/**").hasRole("USER")
                
                
                .anyRequest() //define que a configuraÃ§Ã£o Ã© vÃ¡lida para qualquer requisiÃ§Ã£o.
                .authenticated() //define que o usuÃ¡rio precisa estar autenticado.
                .and()
                .formLogin() //define que a autenticaÃ§Ã£o pode ser feita via formulÃ¡rio de login.
                .loginPage("/login") // passamos como parÃ¢metro a URL para acesso Ã  pÃ¡gina de login que criamos
                .permitAll() //define que essa pÃ¡gina pode ser acessada por todos, independentemente do usuÃ¡rio estar autenticado ou nÃ£o.
        		.and()
        		.logout()
        		.logoutSuccessUrl("/home")//com esse metodo ao fazer logof ele retorna para pagina home
        		.permitAll();
    }

    @Autowired
    public void configureUserDetails(AuthenticationManagerBuilder auth) throws Exception {  	
       //AUTENTICAÇÃO DO BANCO
    	auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    	
    	
    	/*builder AUTENTICAÇÃO FIXA
                .inMemoryAuthentication()
                .withUser("jose").password(new BCryptPasswordEncoder().encode("123")).roles("EDITOR", "ADMIN")
        		.and()
        		.withUser("user").password(new BCryptPasswordEncoder().encode("123")).roles("EDITOR", "USER");*/
    }
    
    
    //$2a$10$1t5Ls0sypJXK5DbL7KCwXOPaXQeTFzL4CGfjBJY7QEWcFFhOHVYUG
     /**
     * Com o mÃ©todo, instanciamos uma instÃ¢ncia do encoder BCrypt e deixando o controle dessa instÃ¢ncia como responsabilidade do Spring.
     * Agora, sempre que o Spring Security necessitar disso, ele jÃ¡ terÃ¡ o que precisa configurado.
     * @return 
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}