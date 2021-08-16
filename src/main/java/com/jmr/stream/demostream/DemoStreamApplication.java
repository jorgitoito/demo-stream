package com.jmr.stream.demostream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Application for Demo and test purpose.
 * <p>
 * targets to cover:
 * <p>
 * JPA.
 * JPA API Criteria
 * OPEN API. API REST.
 * Data validation: ConstraintValidator, jsoup, json schema,...
 * Spring cloud stream. Producer Message -> RabbitMQ -> Consumer
 * Workflow.  1 step triggers 2, 3 and 4;  4 triggers 5 and 6;....
 * Sleuth, logs.
 * JWT
 * cache
 * Unit test:  junit and mockito
 */

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.jmr.stream.demostream")
public class DemoStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoStreamApplication.class, args);
    }

}