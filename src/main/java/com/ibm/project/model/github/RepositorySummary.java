package com.ibm.project.model.github;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe utilizada como resposta a consulta da api do github.
 * 
 * @author bruno
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepositorySummary implements Serializable {

	private static final long serialVersionUID = 6043114138189633207L;

	private Boolean _private;
	
	private String created_at;
	
	private String description;
	
	private String full_name;
	
	private String language;
	
	private String name;
	
	private String owner;
	
	private String updated_at;	
	
}
