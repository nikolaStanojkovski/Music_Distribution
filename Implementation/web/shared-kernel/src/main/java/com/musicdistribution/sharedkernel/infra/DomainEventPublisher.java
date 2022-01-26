package com.musicdistribution.sharedkernel.infra;

import com.musicdistribution.sharedkernel.domain.events.DomainEvent;

/**
 * Abstract interface for a domain event publisher.
 */
public interface DomainEventPublisher {

    /**
     * Method used for publishing an event.
     *
     * @param event - the event to be published.
     */
    void publish(DomainEvent event);
}
