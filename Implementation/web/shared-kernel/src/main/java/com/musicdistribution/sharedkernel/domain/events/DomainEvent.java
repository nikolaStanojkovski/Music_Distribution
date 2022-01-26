package com.musicdistribution.sharedkernel.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.Instant;

/**
 * Abstract class for a domain event.
 */
@Getter
public class DomainEvent {

    private final String topic;
    private final String occurredOn;

    /**
     * Args constructor for the domain event.
     *
     * @param topic - domain event's topic.
     */
    public DomainEvent(String topic) {
        this.occurredOn = Instant.now().toString();
        this.topic = topic;
    }

    /**
     * Method for converting a domain event JSON object to a POJO.
     *
     * @param json       - the JSON string containing the parameters.
     * @param eventClass - the POJO class to be created.
     * @param <E>        - the type of POJO to be created.
     * @return the converted domain event class.
     * @throws JsonProcessingException - if the conversion failed.
     */
    public static <E extends DomainEvent> E fromJson(String json, Class<E> eventClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, eventClass);
    }

    /**
     * Method used for converting the object to a JSON format.
     *
     * @return a string with the object's parameters in JSON format.
     */
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String output = null;
        try {
            output = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ignored) {
        }
        return output;
    }
}
