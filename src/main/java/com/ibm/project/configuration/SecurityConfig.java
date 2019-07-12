package com.ibm.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ibm.project.configuration.jwt.JwtTokenAuthenticationFilter;
import com.ibm.project.configuration.jwt.JwtTokenProvider;

/**
 * Classe reponsável pela configuração de segurança da aplicação.
 * 
 * @author bruno
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()            	
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    
    private JwtTokenAuthenticationFilter JwtTokenAuthenticationFilter() {
    	return new JwtTokenAuthenticationFilter(jwtTokenProvider);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}

