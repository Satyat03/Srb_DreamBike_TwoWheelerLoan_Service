package in.cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CreditManagerCmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditManagerCmApplication.class, args);
		System.out.println("Sanction Letter form");
	}

}
