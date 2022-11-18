package com.musicdistribution.storageservice.xport.rest;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.storageservice.constant.PathConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Email Rest Controller.
 */
@ApiController
@RequestMapping(PathConstants.API_EMAIL_DOMAINS)
public class EmailResource {

    /**
     * Method used for fetching all the email domains.
     *
     * @return the list of email domains.
     */
    @GetMapping
    public List<EmailDomain> getEmailDomains() {
        return List.of(EmailDomain.values());
    }
}
