package com.musicdistribution.albumpublishing.infrastracture;

import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import com.musicdistribution.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of the DomainEventPublisher for the album-publishing module.
 */
@Service
@AllArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(DomainEvent event) {
        this.kafkaTemplate.send(event.getTopic(), event.toJson());
    }
}

