package com.musicdistribution.sharedkernel.util;

import com.musicdistribution.sharedkernel.constants.EnvironmentConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Decorator for all REST API controllers.
 */
@Documented
@RestController
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(origins = EnvironmentConstants.CROSS_ORIGIN_DOMAIN)
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public @interface ApiController {
}
