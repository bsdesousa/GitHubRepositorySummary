package com.ibm.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ibm.project")
public class GitHubRespositorySummaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubRespositorySummaryApplication.class, args);
	}

}
