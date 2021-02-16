package com.uu.searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SearchengineApplication {


//	DocumentTermService documentTermService = BeanUtil.getBean(DocumentTermService.class);


	public static void main(String[] args) {
		SpringApplication.run(SearchengineApplication.class, args);

	}
}
