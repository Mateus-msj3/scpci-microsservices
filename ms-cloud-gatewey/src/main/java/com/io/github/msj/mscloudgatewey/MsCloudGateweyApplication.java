package com.io.github.msj.mscloudgatewey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MsCloudGateweyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCloudGateweyApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder
				.routes()
					.route(r -> r.path("/pessoas/**").uri("lb://mspessoas"))
					.route(r -> r.path("/cursos/**").uri("lb://mscurso"))
					.route(r -> r.path("/inscricoes/**").uri("lb://msinscricao"))
				.build();
	}

}
