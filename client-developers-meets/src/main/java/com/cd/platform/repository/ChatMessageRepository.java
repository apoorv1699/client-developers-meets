package com.cd.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cd.platform.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByThread_IdOrderByCreatedAtAsc(Long threadId);
}
