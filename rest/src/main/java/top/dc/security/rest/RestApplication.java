package top.dc.security.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wenfeng.zhu
 */
@ComponentScan({"springfox.documentation.swagger2", "top.dc.security"})
@SpringBootApplication
public class RestApplication {
    public static void main(String... args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
