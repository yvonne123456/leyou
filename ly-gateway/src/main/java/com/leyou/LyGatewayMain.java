package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class LyGatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(LyGatewayMain.class,args);
        System.out.println(("----网关启动---"));
    }
}
