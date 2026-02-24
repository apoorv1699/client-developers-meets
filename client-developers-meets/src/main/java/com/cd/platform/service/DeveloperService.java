package com.cd.platform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.platform.model.DeveloperProfile;
import com.cd.platform.model.PortfolioProject;
import com.cd.platform.model.User;
import com.cd.platform.repository.DeveloperProfileRepository;
import com.cd.platform.repository.PortfolioProjectRepository;
import com.cd.platform.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class DeveloperService {
    private final DeveloperProfileRepository developerRepository;
    private final PortfolioProjectRepository portfolioRepository;
    private final UserRepository userRepository;

    public DeveloperService(DeveloperProfileRepository developerRepository,
                            PortfolioProjectRepository portfolioRepository,
                            UserRepository userRepository) {
        this.developerRepository = developerRepository;
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    public List<DeveloperProfile> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Optional<DeveloperProfile> getDeveloperById(Long id) {
        return developerRepository.findById(id);
    }

    public List<PortfolioProject> getPortfolioForDeveloper(Long developerId) {
        return portfolioRepository.findByDeveloper_Id(developerId);
    }

    @Transactional
    public DeveloperProfile createDeveloper(Long userId,
                                            String name,
                                            String title,
                                            String bio,
                                            String skills,
                                            String rate,
                                            String location,
                                            String availability) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        DeveloperProfile profile = new DeveloperProfile();
        profile.setUser(user);
        profile.setName(name);
        profile.setTitle(title);
        profile.setBio(bio);
        profile.setSkills(skills);
        profile.setRate(rate);
        profile.setLocation(location);
        profile.setAvailability(availability);
        return developerRepository.save(profile);
    }

    @Transactional
    public DeveloperProfile updateDeveloper(Long developerId,
                                            String name,
                                            String title,
                                            String bio,
                                            String skills,
                                            String rate,
                                            String location,
                                            String availability) {
        DeveloperProfile profile = developerRepository.findById(developerId)
                .orElseThrow(() -> new IllegalArgumentException("Developer not found"));
        profile.setName(name);
        profile.setTitle(title);
        profile.setBio(bio);
        profile.setSkills(skills);
        profile.setRate(rate);
        profile.setLocation(location);
        profile.setAvailability(availability);
        return developerRepository.save(profile);
    }

    @Transactional
    public PortfolioProject addPortfolioProject(Long developerId,
                                                String title,
                                                String description,
                                                String techStack,
                                                String imageUrl) {
        DeveloperProfile developer = developerRepository.findById(developerId)
                .orElseThrow(() -> new IllegalArgumentException("Developer not found"));
        PortfolioProject project = new PortfolioProject();
        project.setDeveloper(developer);
        project.setTitle(title);
        project.setDescription(description);
        project.setTechStack(techStack);
        project.setImageUrl(imageUrl);
        return portfolioRepository.save(project);
    }
}
