package com.nexign.phone_service_app.domain.entity.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public class IdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public IdEntity(final Long id) {
        this.id = id;
    }

    public IdEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof IdEntity other)) {
            return false;
        }

        return other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object id = this.getId();
        result = result * 59 + (id == null ? 43 : id.hashCode());
        return result;
    }

}
