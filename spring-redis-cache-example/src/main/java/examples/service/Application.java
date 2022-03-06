package examples.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        System.out.println("Hello Redis");
        SpringApplication.run(Application.class);
    }
}
