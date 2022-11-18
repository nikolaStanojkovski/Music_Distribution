package com.musicdistribution.sharedkernel.domain.base;

import com.musicdistribution.sharedkernel.constant.ExceptionConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Database entity identifier object.
 *
 * @param <ID> - the type of the identifier.
 */
@Getter
@MappedSuperclass
@NoArgsConstructor
public class AbstractEntity<ID extends DomainObjectId> {

    @EmbeddedId
    private ID id;

    /**
     * Protected args constructor used for creating an abstract entity.
     *
     * @param id - entity's ID from which the object is to be created.
     */
    protected AbstractEntity(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, ExceptionConstants.ENTITY_CREATION_FAILURE);
    }

    /**
     * Method used for checking whether two abstract entities are identical.
     *
     * @param o - the other object to be compared to {this}.
     * @return a flag determining whether the objects are identical or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var other = (AbstractEntity<?>) o;
        return id != null && Objects.equals(id, other.id);
    }

    /**
     * Method used to create hash code for the abstract entity.
     *
     * @return the integer value of the hash code.
     */
    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    /**
     * Method used to construct the string representation of the abstract entity.
     *
     * @return the string representation for the abstract entity.
     */
    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
}
