package com.insurance.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass"})
public class PolicyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyApplication.class, args);
    }
}
