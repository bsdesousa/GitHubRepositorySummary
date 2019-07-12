package com.ibm.project.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.project.exception.NoGitHubUserInfoException;
import com.ibm.project.model.github.GitHubUser;
import com.ibm.project.model.github.RepositorySummary;

/**
 * Serviço que trata das chamadas para api do github.
 * 
 * @author bruno
 *
 */
@Service
public class GitHubService {
	
	
	@Value("${host.api.github}")
	private String apitGithub;
	
	@Autowired
	private ObjectMapper jsonObjectMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
 	public List<RepositorySummary> listarPespositorios(GitHubUser gitHubUser) throws Exception {

 		return acessarGitHubApi(gitHubUser) .stream().map(map -> {
			return 	RepositorySummary.builder()
					.created_at(map.get("created_at").toString())
					._private((Boolean) map.get("private"))
					.description(map.get("description") != null ? map.get("description").toString() : null)
					.full_name(map.get("full_name").toString())
					.language(map.get("language") != null ? map.get("language").toString() : null)
					.name(map.get("name").toString())
					.owner(map.get("owner") .toString())
					.updated_at(map.get("updated_at").toString())
					.build();
		} )
		.collect(Collectors.toList());
 	}
 	
 	private List<Map<String, Object>> acessarGitHubApi(GitHubUser gitHubUser) throws Exception {
 		
 		validateGitHubUser(gitHubUser);
 		
 		System.out.println(Base64Utils.encodeToString(gitHubUser.toString().getBytes()));
 		
 		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("Authorization", "Basic " + Base64Utils.encodeToString(gitHubUser.toString().getBytes()));
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		try{
			ResponseEntity<String> response = restTemplate.exchange(apitGithub, HttpMethod.GET,request, String.class);			
			String jsonRetorno = response.getBody();
			
			return converterJsonToObject(jsonRetorno);			
		}
		catch(HttpStatusCodeException ex) {
			throw new NoGitHubUserInfoException("Usuário ou senha do GitHub inválidos.");
		}		
 	}
 	
 	private void validateGitHubUser(GitHubUser gitHubUser) throws NoGitHubUserInfoException {
		if ( gitHubUser.getUsername() == null || "".equals(gitHubUser.getUsername()) || 
				gitHubUser.getPassword() == null || "".equals(gitHubUser.getPassword()) ) {
			
			throw new NoGitHubUserInfoException("Usuário ou senha do GitHub inválidos.");
			
		}
		
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> converterJsonToObject(String json) throws Exception { 
 		try {
			return jsonObjectMapper.readValue(json, List.class);
		} catch (IOException e) {
			throw new Exception("Ocorreu um erro interno na aplicação.");
		} 		 		
 	}
 	
}
