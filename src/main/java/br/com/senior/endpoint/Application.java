package br.com.senior.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories({
		"br.com.senior.controller"
})
@ComponentScan({
    "br.com.senior.controller",
    "br.com.senior.endpoint"
})
@EntityScan({
		"br.com.senior.model"
})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
