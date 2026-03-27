package com.chaoxi;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chaoxi.mapper")
public class ChaoxiGymApplication {
  public static void main(String[] args) {
    SpringApplication.run(ChaoxiGymApplication.class, args);
  }
}
