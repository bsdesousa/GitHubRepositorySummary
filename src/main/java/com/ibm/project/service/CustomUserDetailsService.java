package com.ibm.project.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Classe utilizada para validação do usuário na aplicação.
 * 
 * @author bruno
 *
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder; // = new BCryptPasswordEncoder(11);
	
	private static final Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("admin"));

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	if (!username.equals("admin")) {		
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
        return new User(username, passwordEncoder.encode("12345"), authorities);
    }
}
