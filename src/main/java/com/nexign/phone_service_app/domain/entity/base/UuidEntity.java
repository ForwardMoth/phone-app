package com.nexign.phone_service_app.domain.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public class UuidEntity extends IdEntity implements Serializable {

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    public UuidEntity() {

    }

    public UuidEntity(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UuidEntity other)) {
            return false;
        }

        return other.getUuid().equals(this.getUuid());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        Object id = this.getUuid();
        result = result * 59 + (id == null ? 43 : id.hashCode());
        return result;
    }

}
