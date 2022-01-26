package com.musicdistribution.sharedkernel.domain.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract class for the domain object ID object.
 */
@Getter
@Embeddable
@MappedSuperclass
@NoArgsConstructor
public class DomainObjectId implements Serializable {

    private String id;

    /**
     * Protected args constructor for the domain object id.
     *
     * @param uuid - the entity's id from which the object is created.
     */
    @JsonCreator
    protected DomainObjectId(@NonNull String uuid) {
        this.id = Objects.requireNonNull(uuid, "uuid must not be null");
    }

    /**
     * Method used for the creation of a random instance of the given idClass.
     *
     * @param idClass - the id class needed for creation of the id.
     * @param <ID>    - the type of id to be created.
     * @return the created random id.
     */
    @NonNull
    public static <ID extends DomainObjectId> ID randomId(@NonNull Class<ID> idClass) {
        Objects.requireNonNull(idClass, "idClass must not be null");
        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception ex) {
            throw new RuntimeException("Could not create new instance of " + idClass, ex);
        }
    }
}
