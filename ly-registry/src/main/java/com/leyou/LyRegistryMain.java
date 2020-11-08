package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LyRegistryMain {
    public static void main(String[] args) {
        SpringApplication.run(LyRegistryMain.class);
        System.out.println(("----注册中心启动-----"));

    }
}
