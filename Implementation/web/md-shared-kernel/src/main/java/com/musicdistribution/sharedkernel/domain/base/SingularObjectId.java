package com.musicdistribution.sharedkernel.domain.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.musicdistribution.sharedkernel.constant.ExceptionConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain object identifier.
 */
@Getter
@Embeddable
@MappedSuperclass
@NoArgsConstructor
public class SingularObjectId implements DomainObjectId {

    private String id;

    /**
     * Protected args constructor used for creating a domain object.
     *
     * @param uuid - entity's ID from which the object is to be created.
     */
    @JsonCreator
    protected SingularObjectId(@NonNull String uuid) {
        this.id = Objects.requireNonNull(uuid, ExceptionConstants.DOMAIN_OBJECT_CREATION_FAILURE);
    }

    /**
     * Method used for the creation of a random instance of an identifier for a given class.
     *
     * @param idClass - the class needed for creation of the ID.
     * @param <ID>    - the type of the ID to be created.
     * @return the generated random ID for the domain object.
     */
    @NonNull
    public static <ID extends SingularObjectId> ID randomId(@NonNull Class<ID> idClass) {
        Objects.requireNonNull(idClass, ExceptionConstants.ID_CLASS_CREATION_FAILURE);
        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception ex) {
            throw new RuntimeException(String.format(ExceptionConstants.CLASS_CREATION_FAILURE, idClass), ex);
        }
    }
}
