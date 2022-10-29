package com.hanghae.code99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //타임스탬프 상속해서 쓰려면 넣어줘야함

@SpringBootApplication
public class Code99Application {

    public static void main(String[] args) {
        SpringApplication.run(Code99Application.class, args);
    }

}
