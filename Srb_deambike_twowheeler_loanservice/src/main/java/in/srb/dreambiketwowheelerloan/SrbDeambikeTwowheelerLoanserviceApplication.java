package in.srb.dreambiketwowheelerloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SrbDeambikeTwowheelerLoanserviceApplication {

	public static void main(String[] args) {
		System.err.println("application start");
		SpringApplication.run(SrbDeambikeTwowheelerLoanserviceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
