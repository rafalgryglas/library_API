package com.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

//    @Autowired
//    private ObjectMapper objectMapper;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
//	@PostConstruct
//	public void setUp() {
//		objectMapper.registerModule(new JavaTimeModule());
//	}

}

