package in.srb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "in.srb")
public class LoanapplicationformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanapplicationformApplication.class, args);
	System.out.println("Loan Application Start !!");
	}
	
	@Bean
	public RestTemplate rt() {
		RestTemplate rs=new RestTemplate();
		return rs;
	}

}
