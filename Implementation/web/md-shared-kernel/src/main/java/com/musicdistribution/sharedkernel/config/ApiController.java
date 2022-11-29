package com.musicdistribution.sharedkernel.config;

import com.musicdistribution.sharedkernel.constant.EnvironmentConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Decorator for all REST API controllers.
 */
@Documented
@RestController
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = EnvironmentConstants.CROSS_ORIGIN_DOMAIN,
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        })
public @interface ApiController {
}
