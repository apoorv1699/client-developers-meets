package com.cd.platform.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.platform.model.ClientProfile;
import com.cd.platform.model.User;
import com.cd.platform.repository.ClientProfileRepository;
import com.cd.platform.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ClientService {
    private final ClientProfileRepository clientRepository;
    private final UserRepository userRepository;

    public ClientService(ClientProfileRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public Optional<ClientProfile> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Transactional
    public ClientProfile createClient(Long userId, String companyName, String industry, String budgetRange) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ClientProfile profile = new ClientProfile();
        profile.setUser(user);
        profile.setCompanyName(companyName);
        profile.setIndustry(industry);
        profile.setBudgetRange(budgetRange);
        return clientRepository.save(profile);
    }

    @Transactional
    public ClientProfile updateClient(Long clientId, String companyName, String industry, String budgetRange) {
        ClientProfile profile = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        profile.setCompanyName(companyName);
        profile.setIndustry(industry);
        profile.setBudgetRange(budgetRange);
        return clientRepository.save(profile);
    }
}
