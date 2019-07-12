package com.ibm.project.exception;

/**
 * Classe que trata da exceção para usuário inválido do gitHub. 
 * 
 * @author bruno
 *
 */
public class NoGitHubUserInfoException extends Exception {

	private static final long serialVersionUID = 6283074489041201820L;

	public NoGitHubUserInfoException() {
		
	}

	public NoGitHubUserInfoException(String message) {
		super(message);
	}
}
