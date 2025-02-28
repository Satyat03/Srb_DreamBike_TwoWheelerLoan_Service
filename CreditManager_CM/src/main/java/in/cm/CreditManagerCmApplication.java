package in.cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("in.cm.model")

public class CreditManagerCmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditManagerCmApplication.class, args);
		System.out.println("Sanction Letter form");
	}

}
