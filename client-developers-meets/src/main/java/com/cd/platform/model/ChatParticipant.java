package com.cd.platform.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_participants")
public class ChatParticipant {
    @EmbeddedId
    private ChatParticipantId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("threadId")
    @JoinColumn(name = "thread_id")
    private ChatThread thread;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public ChatParticipantId getId() {
        return id;
    }

    public void setId(ChatParticipantId id) {
        this.id = id;
    }

    public ChatThread getThread() {
        return thread;
    }

    public void setThread(ChatThread thread) {
        this.thread = thread;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
