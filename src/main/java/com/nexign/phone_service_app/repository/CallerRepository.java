package com.nexign.phone_service_app.repository;

import com.nexign.phone_service_app.domain.entity.Caller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CallerRepository extends JpaRepository<Caller, Long> {

    Optional<Caller> findByUuid(UUID id);

}
