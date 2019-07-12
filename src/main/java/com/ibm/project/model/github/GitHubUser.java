package com.ibm.project.model.github;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe que recebe o usuario e a senha do github.
 * 
 * @author bruno
 *
 */
@Data
@Builder
@Getter
public class GitHubUser implements Serializable {
	
	private static final long serialVersionUID = -4573880196165461097L;

	private String username;
	
	private String password;

	@Override
	public String toString() {
		return String.join(":", username,password);
	}
	
	
	
	
}
