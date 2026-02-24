package com.cd.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cd.platform.model.ChatParticipant;
import com.cd.platform.model.ChatParticipantId;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, ChatParticipantId> {
    List<ChatParticipant> findByUser_Id(Long userId);
    List<ChatParticipant> findByThread_Id(Long threadId);
}
