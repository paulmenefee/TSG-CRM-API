package com.tsg.restfulservice;

import com.tsg.restfulservice.controller.ClientController;
import com.tsg.restfulservice.model.ClientDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
