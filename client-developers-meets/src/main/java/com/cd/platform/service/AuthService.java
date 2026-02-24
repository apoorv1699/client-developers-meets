package com.cd.platform.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.platform.model.ClientProfile;
import com.cd.platform.model.DeveloperProfile;
import com.cd.platform.model.User;
import com.cd.platform.model.UserRole;
import com.cd.platform.repository.ClientProfileRepository;
import com.cd.platform.repository.DeveloperProfileRepository;
import com.cd.platform.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final DeveloperProfileRepository developerRepository;
    private final ClientProfileRepository clientRepository;
    private final DeveloperService developerService;
    private final ClientService clientService;

    public AuthService(UserRepository userRepository,
                       DeveloperProfileRepository developerRepository,
                       ClientProfileRepository clientRepository,
                       DeveloperService developerService,
                       ClientService clientService) {
        this.userRepository = userRepository;
        this.developerRepository = developerRepository;
        this.clientRepository = clientRepository;
        this.developerService = developerService;
        this.clientService = clientService;
    }

    public LoginResult login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!user.getPasswordHash().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        Long developerId = null;
        Long clientId = null;
        if (user.getRole() == UserRole.DEVELOPER) {
            developerId = developerRepository.findByUser_Id(user.getId()).map(DeveloperProfile::getId).orElse(null);
        } else if (user.getRole() == UserRole.CLIENT) {
            clientId = clientRepository.findByUser_Id(user.getId()).map(ClientProfile::getId).orElse(null);
        }
        return new LoginResult(user, developerId, clientId);
    }

    @Transactional
    public SignupResult signup(SignupRequest request) {
        Optional<User> existing = userRepository.findByEmail(request.email());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setRole(request.role());
        user.setEmail(request.email());
        user.setPasswordHash(request.password());
        user = userRepository.save(user);

        Long developerId = null;
        Long clientId = null;

        if (request.role() == UserRole.DEVELOPER) {
            DeveloperProfile profile = developerService.createDeveloper(
                    user.getId(),
                    request.name(),
                    request.title(),
                    request.bio(),
                    request.skills(),
                    request.rate(),
                    request.location(),
                    request.availability()
            );
            developerId = profile.getId();
            if (request.projectTitle() != null && !request.projectTitle().isBlank()) {
                developerService.addPortfolioProject(
                        developerId,
                        request.projectTitle(),
                        request.projectDescription(),
                        request.projectTechStack(),
                        request.projectImageUrl()
                );
            }
        } else if (request.role() == UserRole.CLIENT) {
            ClientProfile profile = clientService.createClient(
                    user.getId(),
                    request.companyName(),
                    request.industry(),
                    request.budgetRange()
            );
            clientId = profile.getId();
        }

        return new SignupResult(user, developerId, clientId);
    }

    public record LoginResult(User user, Long developerId, Long clientId) {
    }

    public record SignupResult(User user, Long developerId, Long clientId) {
    }

    public record SignupRequest(UserRole role,
                                String email,
                                String password,
                                String name,
                                String title,
                                String bio,
                                String skills,
                                String rate,
                                String location,
                                String availability,
                                String companyName,
                                String industry,
                                String budgetRange,
                                String projectTitle,
                                String projectDescription,
                                String projectTechStack,
                                String projectImageUrl) {
    }
}
