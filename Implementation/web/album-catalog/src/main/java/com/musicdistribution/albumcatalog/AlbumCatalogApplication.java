package com.musicdistribution.albumcatalog;

import com.musicdistribution.albumcatalog.config.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@SpringBootApplication
//@EnableConfigurationProperties({
//        FileProperties.class
//})
public class AlbumCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlbumCatalogApplication.class, args);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
