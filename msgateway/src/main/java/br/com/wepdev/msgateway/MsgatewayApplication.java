package br.com.wepdev.msgateway;

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
public class MsgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgatewayApplication.class, args);
	}


	/**
	 * Objeto que faz o roteamento das rotas direto para discovery server(eureka server)
	 * @param builder
	 * @return
	 */
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
				// Toda vez que for feita uma requisição para /clientes sera feito o redirecionamento para o load balancer(lb) e para o MS de cliente
				.route( r -> r.path("/clientes/**").uri("lb://msclientes") )
				.route( r -> r.path("/cartoes/**").uri("lb://mscartoes") )
				.route( r -> r.path("/avaliacoes-credito/**").uri("lb://msavaliadorcredito") )
				.build();
	}

}
