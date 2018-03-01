package br.com.joaoverissimo.curso.pontointeligente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiRestfulSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRestfulSpringBootApplication.class, args);
    }
}
