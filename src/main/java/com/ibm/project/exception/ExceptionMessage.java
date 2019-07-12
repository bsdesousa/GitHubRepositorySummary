package com.ibm.project.exception;

import java.util.List;

import lombok.Getter;

/**
 * Classe que recebe a mensagem de exceção para ser convertida em json.
 * 
 * @author bruno
 *
 */
@Getter
public class ExceptionMessage {
	private String message;
	private List<String> messages;

	public ExceptionMessage(List<String> messages) {
		this.messages = messages;
	}

	public ExceptionMessage(String message) {
		this.message = message;
	}

}
