package com.project.computingconcept.searchengine;

import com.project.computingconcept.searchengine.Constants.CreateTrie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
		CreateTrie.initTrie();
	}

}
