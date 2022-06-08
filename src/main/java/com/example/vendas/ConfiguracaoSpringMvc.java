package com.example.vendas;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ConfiguracaoSpringMvc implements WebMvcConfigurer {
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      /*  registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");*/
        
        //registry.addViewController("/").setViewName("produto/list");//produtos
    	//registry.addViewController("/home").setViewName("produto/list");
        registry.addRedirectViewController("/", "produtos/list");
        registry.addRedirectViewController("/home", "produtos/list");    
       
    }
}