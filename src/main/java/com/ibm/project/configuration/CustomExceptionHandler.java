package com.ibm.project.configuration;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ibm.project.exception.ExceptionMessage;
import com.ibm.project.exception.NoGitHubUserInfoException;

/**
 * Classe reponsável por converter as exceções para json.
 * 
 * @author bruno
 *
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String UNEXPECTED_ERROR = "Ocorreu um erro! Entre em contato com suporte.";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		return new ResponseEntity<>(new ExceptionMessage(errors), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoGitHubUserInfoException.class)
	public ResponseEntity<ExceptionMessage> handleIllegalArgument(NoGitHubUserInfoException ex) {
		return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionMessage> handleAccessDeniedException(AccessDeniedException ex) {
		return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionMessage> handleBadCredentialsException(BadCredentialsException ex) {
		return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessage> handleExceptions(Exception ex, Locale locale) {
		String errorMessage = UNEXPECTED_ERROR;
		ex.printStackTrace();
		return new ResponseEntity<>(new ExceptionMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
