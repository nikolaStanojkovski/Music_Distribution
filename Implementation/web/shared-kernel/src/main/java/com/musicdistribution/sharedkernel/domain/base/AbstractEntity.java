package com.musicdistribution.sharedkernel.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Abstract class for ID entity object.
 *
 * @param <ID> - the main identifier for the entity.
 */
@Getter
@MappedSuperclass
@NoArgsConstructor
public class AbstractEntity<ID extends DomainObjectId> {

    @EmbeddedId
    private ID id;

    /**
     * Copy constructor.
     *
     * @param source the entity to copy from.
     */
    protected AbstractEntity(@NonNull AbstractEntity<ID> source) {
        Objects.requireNonNull(source, "source must not be null");
        this.id = source.id;
    }

    /**
     * Protected args constructors.
     *
     * @param id - the entity's id from which the object is created.
     */
    protected AbstractEntity(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

    /**
     * Method used for checking whether two abstract entities are equal.
     *
     * @param obj - the other object to be compared to this.
     * @return a flag of whether this object is equal to the one passed as an argument.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        var other = (AbstractEntity<?>) obj;
        return id != null && id.equals(other.id);
    }

    /**
     * Method used for creation of the hash code for the abstract entity.
     *
     * @return the integer value of the hash code.
     */
    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    /**
     * The toString notation for the abstract entity.
     *
     * @return the string representation for the abstract entity.
     */
    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
}
