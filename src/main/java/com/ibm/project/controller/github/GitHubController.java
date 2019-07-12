package com.ibm.project.controller.github;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.project.model.github.GitHubUser;
import com.ibm.project.model.github.RepositorySummary;
import com.ibm.project.service.GitHubService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

/**
 * Classe responsável por implementar o serviço rest para obter o resumo do repositório 
 * de um usuário do github. 
 * 
 * @author bruno
 *
 */
@RestController
@RequestMapping
public class GitHubController {
	
	@Autowired
	private GitHubService gitHubService;
	
	@PostMapping(value = "/list")
	@ApiOperation(value = "Lista todos os repositórios do GitHub de um determinado usuário", produces = "*/*", tags = {"git-hub-controller"}, authorizations = {
			@Authorization(value = "authKey", scopes = { @AuthorizationScope(scope = "global", description = "")  })
	})
	public List<RepositorySummary> index(@RequestBody(required = true) GitHubUser user) throws Exception {
		
		return gitHubService.listarPespositorios(user);
	}
	
	

}
