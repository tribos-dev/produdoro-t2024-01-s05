package dev.wakandaacademy.produdoro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class ProdudoroApplication {
	@GetMapping
	public String getHomeTeste() {
		return "Sprint Produdoro _ API Home";
	}

	public static void main(String[] args) {
		SpringApplication.run(ProdudoroApplication.class, args);
	}

}
