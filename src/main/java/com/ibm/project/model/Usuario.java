package com.ibm.project.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa o usuário que será passado para authenticalçao no sistema.
 * 
 * @author bruno
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 3567562887232716137L;
		
	private String username;	
    private String password;

}
