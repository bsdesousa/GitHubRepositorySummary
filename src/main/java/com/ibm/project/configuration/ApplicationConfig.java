package com.ibm.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Classe responsável por definir beans padrões para aplicação.
 * 
 * @author bruno
 *
 */
@Configuration
public class ApplicationConfig {
	
	@Bean 
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

}
