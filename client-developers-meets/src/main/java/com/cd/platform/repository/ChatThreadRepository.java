package com.cd.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cd.platform.model.ChatThread;

public interface ChatThreadRepository extends JpaRepository<ChatThread, Long> {
    @Query("select t from ChatThread t join ChatParticipant p on p.thread = t where p.user.id = :userId")
    List<ChatThread> findThreadsForUser(@Param("userId") Long userId);
}
