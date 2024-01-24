package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.ConsultRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultRequestRepository extends JpaRepository<ConsultRequest,Long> {
    List<ConsultRequest> findByPlannerIdOrderByRequestTimeDesc(Long plannerId);

    Optional<ConsultRequest> findByPlannerIdAndChatRoomIdAndId(Long plannerID, UUID chatRoomId, Long id);

}
