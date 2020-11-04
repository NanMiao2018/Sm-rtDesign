package com.dcits;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 *  @author nanmiaoa
 *  @data 2020/10/27 9:00
 */
@EnableAutoConfiguration
@ComponentScan("com.dcits")
@MapperScan("com.dcits.dao")      // MyBatis Mapper接口文件所在包路径
//@SpringBootApplication
public class StartApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StartApp.class, args);
    }
}
