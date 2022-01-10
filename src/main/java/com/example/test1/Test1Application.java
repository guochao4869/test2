package com.example.test1;

import com.example.test1.pojo.Result;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * @author OuLa-test
 */
@SpringBootApplication
@MapperScan("com.example.test1.mapper")
@Slf4j
public class Test1Application {

    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
    }

    /*@Bean
    public void startService(){
        log.info("开始启动服务器");
        try {
            new com.example.test1.demo.server.ChatServer().startServer();
        }catch (Exception e) {
            e.printStackTrace();
            log.error("启动服务器出现异常");
        }
    }*/

}
