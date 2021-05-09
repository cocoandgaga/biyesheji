package com.zxj.finalexam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zxj.finalexam.inf")
@SpringBootApplication
@MapperScan("com.zxj.finalexam.dao")
public class KtxFinalexamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KtxFinalexamApplication.class,args);
    }
}
