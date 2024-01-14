package cn.org.bai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description 主程入口
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class FmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmsApplication.class, args);
    }

}

