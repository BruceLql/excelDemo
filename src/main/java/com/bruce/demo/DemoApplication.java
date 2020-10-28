package com.bruce.demo;

import com.bruce.demo.utils.JsonSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liqilan
 */
@Slf4j
@SpringBootApplication
public class DemoApplication implements JsonSupport {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
