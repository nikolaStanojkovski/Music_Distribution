package finki.ukim.mk.emtproject.sharedkernel.infra;

import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
