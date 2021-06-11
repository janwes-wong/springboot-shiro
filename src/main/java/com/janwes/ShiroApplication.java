package com.janwes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes
 * @date 2021/5/22 17:10
 * @description
 */
@SpringBootApplication
@MapperScan("com.janwes.mapper")
public class ShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class, args);
    }
}
