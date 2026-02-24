package com.cd.platform.api;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cd.platform.model.ChatMessage;
import com.cd.platform.model.ChatParticipant;
import com.cd.platform.model.ChatThread;
import com.cd.platform.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {
    private final ChatService chatService;

    public ChatApiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/threads")
    public List<ChatThreadDto> getThreads(@RequestParam Long userId) {
        return chatService.getThreadsForUser(userId).stream().map(thread -> {
            List<Long> participantIds = chatService.getParticipantsForThread(thread.getId()).stream()
                    .map(ChatParticipant::getUser)
                    .map(user -> user == null ? null : user.getId())
                    .toList();
            return new ChatThreadDto(thread.getId(), thread.getUpdatedAt(), participantIds);
        }).toList();
    }

    @GetMapping("/threads/{threadId}/messages")
    public List<ChatMessageDto> getMessages(@PathVariable Long threadId) {
        return chatService.getMessagesForThread(threadId).stream().map(ChatMessageDto::from).toList();
    }

    @PostMapping("/threads")
    public ChatThreadDto createThread(@Valid @RequestBody CreateThreadRequest request) {
        ChatThread thread = chatService.createThread(request.userIds());
        return new ChatThreadDto(thread.getId(), thread.getUpdatedAt(), request.userIds());
    }

    @PostMapping("/threads/{threadId}/messages")
    public ChatMessageDto createMessage(@PathVariable Long threadId,
                                        @Valid @RequestBody CreateMessageRequest request) {
        ChatMessage message = chatService.createMessage(threadId, request.senderId(), request.content());
        return ChatMessageDto.from(message);
    }

    public record ChatThreadDto(Long id, LocalDateTime updatedAt, List<Long> participantIds) {
    }

    public record ChatMessageDto(Long id, Long threadId, Long senderId, String content, LocalDateTime createdAt) {
        public static ChatMessageDto from(ChatMessage message) {
            Long senderId = message.getSender() == null ? null : message.getSender().getId();
            Long threadId = message.getThread() == null ? null : message.getThread().getId();
            return new ChatMessageDto(message.getId(), threadId, senderId, message.getContent(), message.getCreatedAt());
        }
    }

    public record CreateThreadRequest(@NotEmpty List<@NotNull Long> userIds) {
    }

    public record CreateMessageRequest(@NotNull Long senderId, @NotBlank String content) {
    }
}
