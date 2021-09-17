package com.smart.im.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author frankq
 * @date 2021/9/17
 */
@Slf4j
@Configuration
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClientApplication.class);
    }

}
