package com.cd.platform.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ChatParticipantId implements Serializable {
    @Column(name = "thread_id")
    private Long threadId;

    @Column(name = "user_id")
    private Long userId;

    public ChatParticipantId() {
    }

    public ChatParticipantId(Long threadId, Long userId) {
        this.threadId = threadId;
        this.userId = userId;
    }

    public Long getThreadId() {
        return threadId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatParticipantId)) {
            return false;
        }
        ChatParticipantId that = (ChatParticipantId) o;
        return Objects.equals(threadId, that.threadId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threadId, userId);
    }
}
