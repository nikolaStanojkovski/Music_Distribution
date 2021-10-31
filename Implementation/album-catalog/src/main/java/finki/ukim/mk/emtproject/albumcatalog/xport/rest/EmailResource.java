package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import finki.ukim.mk.emtproject.sharedkernel.util.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Email Rest Controller.
 */
@ApiController
@RequestMapping("/api/emailDomains")
public class EmailResource {

    /**
     * Method for getting all the email domains.
     *
     * @return the list of email domains.
     */
    @GetMapping
    public List<EmailDomain> getEmailDomains() {
        return List.of(EmailDomain.values());
    }
}
