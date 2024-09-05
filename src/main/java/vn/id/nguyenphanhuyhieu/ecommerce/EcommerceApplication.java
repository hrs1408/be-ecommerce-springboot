package vn.id.nguyenphanhuyhieu.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import vn.id.nguyenphanhuyhieu.ecommerce.config.WebSocketConfig;

@SpringBootApplication
@Import(WebSocketConfig.class)
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
