package com.diunuge.govtech.repository;

import com.diunuge.govtech.model.Session;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends JpaRepository<Session, Long> {

  @Query("SELECT DISTINCT s FROM Session s " +
      "LEFT JOIN s.initiator i " +
      "LEFT JOIN s.participants p " +
      "LEFT JOIN s.participantsInvited pi " +
      "WHERE i.id = :userId OR p.id = :userId OR pi.id = :userId")
  List<Session> findSessionsForUser(@Param("userId") Long userId);
}
