package com.configServer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerSrbApplication {

	public static void main(String[] args) 
	{
		
		SpringApplication.run(ConfigServerSrbApplication.class, args);
	}

}
