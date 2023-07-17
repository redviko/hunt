package test.hunt_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HuntTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuntTestApplication.class, args);
    }

}

