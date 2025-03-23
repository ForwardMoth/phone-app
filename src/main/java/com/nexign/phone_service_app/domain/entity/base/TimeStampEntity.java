package com.nexign.phone_service_app.domain.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
public class TimeStampEntity extends UuidEntity implements Serializable {

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    protected ZonedDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    protected ZonedDateTime updated;

    public TimeStampEntity() {
    }

    public TimeStampEntity(final ZonedDateTime created, final ZonedDateTime updated) {
        this.created = created;
        this.updated = updated;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TimeStampEntity other)) {
            return false;
        }

        if (this.getCreated() == null && other.getCreated() != null) {
            return false;
        } else if (!this.getCreated().equals(other.getCreated())) {
            return false;
        }

        if (this.getUpdated() == null && other.getUpdated() != null) {
            return false;
        } else {
            return this.getUpdated().equals(other.getUpdated());
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        Object created = this.getCreated();
        result = result * 59 + (created == null ? 43 : created.hashCode());
        Object updated = this.getUpdated();
        result = result * 59 + (updated == null ? 43 : updated.hashCode());
        return result;

    }

}
