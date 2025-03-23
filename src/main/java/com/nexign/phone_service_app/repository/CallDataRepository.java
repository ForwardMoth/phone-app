package com.nexign.phone_service_app.repository;

import com.nexign.phone_service_app.domain.entity.CallData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallDataRepository extends JpaRepository<CallData, Long> {

    Optional<CallData> findTopByOrderByIdDesc();

    @Query("SELECT c FROM CallData c WHERE c.incomingCaller.id = :callerId OR c.outcomingCaller.id = :callerId")
    List<CallData> findByCaller(@Param("callerId") Long callerId);

    @Query("SELECT c FROM CallData c WHERE (c.incomingCaller.id = :callerId OR c.outcomingCaller.id = :callerId)" +
            "AND c.startCallTime >= :startDateTime AND c.finishCallTime <= :finishDateTime")
    List<CallData> findByCallerAndPeriod(@Param("callerId") Long callerId,
                                         @Param("startDateTime") LocalDateTime startDateTime,
                                         @Param("finishDateTime") LocalDateTime finishDateTime);

    @Query("SELECT c FROM CallData c WHERE c.startCallTime >= :startDateTime AND c.finishCallTime <= :finishDateTime")
    List<CallData> findByPeriod(LocalDateTime startDateTime, LocalDateTime finishDateTime);

}
