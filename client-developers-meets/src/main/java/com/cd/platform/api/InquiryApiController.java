package com.cd.platform.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.platform.model.Inquiry;
import com.cd.platform.service.InquiryService;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryApiController {
    private final InquiryService inquiryService;

    public InquiryApiController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<InquiryDto> createInquiry(@Valid @RequestBody InquiryCreateRequest request) {
        Inquiry inquiry = inquiryService.createInquiry(
                request.clientId(),
                request.developerId(),
                request.projectSummary(),
                request.status()
        );
        return ResponseEntity.ok(InquiryDto.from(inquiry));
    }

    public record InquiryDto(Long id, Long clientId, Long developerId, String projectSummary, String status) {
        public static InquiryDto from(Inquiry inquiry) {
            Long clientId = inquiry.getClient() == null ? null : inquiry.getClient().getId();
            Long developerId = inquiry.getDeveloper() == null ? null : inquiry.getDeveloper().getId();
            return new InquiryDto(inquiry.getId(), clientId, developerId, inquiry.getProjectSummary(), inquiry.getStatus());
        }
    }

    public record InquiryCreateRequest(@NotNull Long clientId,
                                       @NotNull Long developerId,
                                       @NotBlank String projectSummary,
                                       String status) {
    }
}
