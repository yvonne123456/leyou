package com.leyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ={"com.leyou.common.advice","com.leyou.item"})
@MapperScan("com.leyou.item.mapper")
public class LyItemMain {
    public static void main(String[] args) {
        SpringApplication.run(LyItemMain.class,args);
        System.out.println(("----乐优服务端启动----"));

    }
}
