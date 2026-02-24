package com.cd.platform.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.platform.model.ChatMessage;
import com.cd.platform.model.ChatParticipant;
import com.cd.platform.model.ChatThread;
import com.cd.platform.model.User;
import com.cd.platform.repository.ChatMessageRepository;
import com.cd.platform.repository.ChatParticipantRepository;
import com.cd.platform.repository.ChatThreadRepository;
import com.cd.platform.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ChatService {
    private final ChatThreadRepository threadRepository;
    private final ChatParticipantRepository participantRepository;
    private final ChatMessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatService(ChatThreadRepository threadRepository,
                       ChatParticipantRepository participantRepository,
                       ChatMessageRepository messageRepository,
                       UserRepository userRepository) {
        this.threadRepository = threadRepository;
        this.participantRepository = participantRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<ChatThread> getThreadsForUser(Long userId) {
        return threadRepository.findThreadsForUser(userId);
    }

    public List<ChatParticipant> getParticipantsForThread(Long threadId) {
        return participantRepository.findByThread_Id(threadId);
    }

    public List<ChatMessage> getMessagesForThread(Long threadId) {
        return messageRepository.findByThread_IdOrderByCreatedAtAsc(threadId);
    }

    @Transactional
    public ChatThread createThread(List<Long> userIds) {
        ChatThread thread = threadRepository.save(new ChatThread());
        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            ChatParticipant participant = new ChatParticipant();
            participant.setId(new com.cd.platform.model.ChatParticipantId(thread.getId(), userId));
            participant.setThread(thread);
            participant.setUser(user);
            participantRepository.save(participant);
        }
        return thread;
    }

    @Transactional
    public ChatMessage createMessage(Long threadId, Long senderId, String content) {
        ChatThread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ChatMessage message = new ChatMessage();
        message.setThread(thread);
        message.setSender(sender);
        message.setContent(content);
        return messageRepository.save(message);
    }
}
