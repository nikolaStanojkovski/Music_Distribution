package com.musicdistribution.sharedkernel.domain.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.musicdistribution.sharedkernel.constant.ExceptionConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Composite object identifier.
 */
@Getter
@Embeddable
@MappedSuperclass
@NoArgsConstructor
public class CompositeObjectId implements DomainObjectId {

    private String uuid1;
    private String uuid2;

    /**
     * Protected args constructor used for creating a domain object.
     *
     * @param uuid1 - ID from the first entity for the object to be created.
     * @param uuid2 - ID from the second entity for the object to be created.
     */
    @JsonCreator
    protected CompositeObjectId(@NonNull String uuid1, @NonNull String uuid2) {
        this.uuid1 = Objects.requireNonNull(uuid1, ExceptionConstants.DOMAIN_OBJECT_CREATION_FAILURE);
        this.uuid2 = Objects.requireNonNull(uuid2, ExceptionConstants.DOMAIN_OBJECT_CREATION_FAILURE);
    }
}
