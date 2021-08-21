package finki.ukim.mk.emtproject.albumpublishing.infrastracture;

import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;
import finki.ukim.mk.emtproject.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * DomainEventPublisherImpl - Impelentation of the DomainEventPublisher for the album-publishing module
 */
@Service
@AllArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void publish(DomainEvent event) {
        this.kafkaTemplate.send(event.topic(),event.toJson());
    }
}

