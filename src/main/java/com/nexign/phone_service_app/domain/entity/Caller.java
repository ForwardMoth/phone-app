package com.nexign.phone_service_app.domain.entity;

import com.nexign.phone_service_app.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

/**
 * Справочник абонентов
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "callers")
public class Caller extends BaseEntity {

    /**
     * Список входящих звонков
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "incomingCaller", fetch = FetchType.LAZY)
    private List<CallData> incomingCallData;

    /**
     * Список исходящих звонков
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "outcomingCaller", fetch = FetchType.LAZY)
    private List<CallData> outcomingCallData;

    /**
     * Номер абонента
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        Caller caller = (Caller) o;
        return getId() != null && getId().equals(caller.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
