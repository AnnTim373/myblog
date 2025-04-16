package ru.practicum.myblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class MyBlogApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyBlogApp.class);
        ConfigurableApplicationContext context = app.run(args);
        Environment env = context.getEnvironment();
        log.info("""
                        
                        ----------------------------------------------------------
                        \t\
                        Application '{}' is running!.
                        \tAccess URLs:
                        \t\
                        Local: \t\thttp://localhost:{}
                        \t\
                        Swagger: \thttp://localhost:{}/swagger-ui.html
                        \t\
                        
                        ----------------------------------------------------------
                        \t""",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getProperty("server.port"));
    }

}
