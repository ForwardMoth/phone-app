package com.nexign.phone_service_app.domain.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public class BaseEntity extends TimeStampEntity implements Serializable {

    @Column(name = "actual", nullable = false)
    private boolean actual = true;

    public BaseEntity() {
    }

    public BaseEntity(boolean actual) {
        this.actual = actual;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BaseEntity other)) {
            return false;
        }

        return other.actual == this.actual;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 59 + (actual ? 79 : 97);
        return result;
    }
}
