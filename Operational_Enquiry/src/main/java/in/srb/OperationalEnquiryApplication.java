package in.srb;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OperationalEnquiryApplication {

	public static void main(String[] args) {
		System.out.println("application start");
		SpringApplication.run(OperationalEnquiryApplication.class, args);
	}

}
