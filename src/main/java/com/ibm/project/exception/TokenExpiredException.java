package com.ibm.project.exception;

import javax.servlet.ServletException;

/**
 * Classe que trata a exceção do token.
 * 
 * @author bruno
 *
 */
public class TokenExpiredException extends ServletException {

	private static final long serialVersionUID = 6283074489041201820L;

	public TokenExpiredException() {
		
	}

	public TokenExpiredException(String message) {
		super(message);
	}
}
