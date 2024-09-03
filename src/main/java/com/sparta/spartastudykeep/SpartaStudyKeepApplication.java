package com.sparta.spartastudykeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartaStudyKeepApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaStudyKeepApplication.class, args);
    }

}
