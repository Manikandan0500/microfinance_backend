package com.bbots.mfin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.bbots.mfin")
@EnableJpaRepositories(basePackages = "com.bbots.mfin")
@EntityScan(basePackages = "com.bbots.mfin")
public class MicrofinanceBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicrofinanceBackendApplication.class, args);
    }
}
