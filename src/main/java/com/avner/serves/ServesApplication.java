package com.avner.serves;

import com.avner.serves.enums.Status;
import com.avner.serves.models.Server;
import com.avner.serves.repos.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServesApplication.class, args);
	}


}
