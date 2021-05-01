package com.crio.cred.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

/**
 * The common properties for all entities are present in this class.
 *
 * @author harikesh.pallantla
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    /**
     * The creation time.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdOn;

    /**
     * The last updated time.
     */
    @UpdateTimestamp
    private OffsetDateTime lastUpdatedOn;
}
