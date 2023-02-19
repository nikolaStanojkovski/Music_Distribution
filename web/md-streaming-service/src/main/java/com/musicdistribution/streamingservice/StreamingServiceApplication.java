package com.musicdistribution.streamingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 * The main application runner class.
 */
@SpringBootApplication
public class StreamingServiceApplication {

    /**
     * The method used to run the application.
     *
     * @param args - the arguments passed to the Spring Application.
     */
    public static void main(String[] args) {
        SpringApplication.run(StreamingServiceApplication.class, args);
    }

    /**
     * Method used to configure the resolving of multipart files in endpoints.
     *
     * @return the configured multipart resolver.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
