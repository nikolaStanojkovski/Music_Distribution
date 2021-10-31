package com.musicdistribution.sharedkernel.infra;

import com.musicdistribution.sharedkernel.domain.events.DomainEvent;

/**
 * DomainEventPublisher - abstract interface for a domain event publisher
 */
public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
