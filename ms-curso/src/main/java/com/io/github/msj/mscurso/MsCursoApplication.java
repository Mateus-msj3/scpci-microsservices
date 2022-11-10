package com.io.github.msj.mscurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCursoApplication.class, args);
	}

}
