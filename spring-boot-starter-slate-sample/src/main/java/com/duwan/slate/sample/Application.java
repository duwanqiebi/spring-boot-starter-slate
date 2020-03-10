package com.duwan.slate.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.duwan.slate.EnableSlate;

/**
 * @author duwan.zq
 * @date 2020/3/10
 */
@SpringBootApplication
@EnableSlate
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
