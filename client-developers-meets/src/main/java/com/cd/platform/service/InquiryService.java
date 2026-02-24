package com.cd.platform.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.platform.model.ClientProfile;
import com.cd.platform.model.DeveloperProfile;
import com.cd.platform.model.Inquiry;
import com.cd.platform.repository.ClientProfileRepository;
import com.cd.platform.repository.DeveloperProfileRepository;
import com.cd.platform.repository.InquiryRepository;

@Service
@Transactional(readOnly = true)
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final ClientProfileRepository clientRepository;
    private final DeveloperProfileRepository developerRepository;

    public InquiryService(InquiryRepository inquiryRepository,
                          ClientProfileRepository clientRepository,
                          DeveloperProfileRepository developerRepository) {
        this.inquiryRepository = inquiryRepository;
        this.clientRepository = clientRepository;
        this.developerRepository = developerRepository;
    }

    @Transactional
    public Inquiry createInquiry(Long clientId, Long developerId, String projectSummary, String status) {
        ClientProfile client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        DeveloperProfile developer = developerRepository.findById(developerId)
                .orElseThrow(() -> new IllegalArgumentException("Developer not found"));
        Inquiry inquiry = new Inquiry();
        inquiry.setClient(client);
        inquiry.setDeveloper(developer);
        inquiry.setProjectSummary(projectSummary);
        inquiry.setStatus(status);
        return inquiryRepository.save(inquiry);
    }
}
