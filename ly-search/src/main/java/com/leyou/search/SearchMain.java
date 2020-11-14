package com.leyou.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchMain {
    public static void main(String[] args) {
        SpringApplication.run(SearchMain.class,args);
        System.out.println(("----搜索服务启动----"));
    }
}
