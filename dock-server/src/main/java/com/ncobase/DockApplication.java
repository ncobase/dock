package com.ncobase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * Startup Application
 *
 * @author Lion Li
 */
@Slf4j
@SpringBootApplication
public class DockApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DockApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
    }
}
