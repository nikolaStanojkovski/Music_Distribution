package finki.ukim.mk.emtproject.sharedkernel.infra;

import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;

/**
 * DomainEventPublisher - abstract interface for a domain event publisher
 */
public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
