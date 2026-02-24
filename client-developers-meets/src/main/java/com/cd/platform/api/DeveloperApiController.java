package com.cd.platform.api;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cd.platform.model.DeveloperProfile;
import com.cd.platform.model.PortfolioProject;
import com.cd.platform.service.DeveloperService;

@RestController
@RequestMapping("/api/developers")
public class DeveloperApiController {
    private final DeveloperService developerService;

    public DeveloperApiController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public List<DeveloperDto> getDevelopers() {
        return developerService.getAllDevelopers().stream().map(DeveloperDto::from).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDto> getDeveloper(@PathVariable Long id) {
        return developerService.getDeveloperById(id)
                .map(DeveloperDto::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DeveloperDto> createDeveloper(@Valid @RequestBody DeveloperCreateRequest request) {
        DeveloperProfile profile = developerService.createDeveloper(
                request.userId(),
                request.name(),
                request.title(),
                request.bio(),
                request.skills(),
                request.rate(),
                request.location(),
                request.availability()
        );
        return ResponseEntity.ok(DeveloperDto.from(profile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDto> updateDeveloper(@PathVariable Long id,
                                                        @Valid @RequestBody DeveloperUpdateRequest request) {
        DeveloperProfile profile = developerService.updateDeveloper(
                id,
                request.name(),
                request.title(),
                request.bio(),
                request.skills(),
                request.rate(),
                request.location(),
                request.availability()
        );
        return ResponseEntity.ok(DeveloperDto.from(profile));
    }

    @GetMapping("/{id}/portfolio")
    public List<PortfolioDto> getPortfolio(@PathVariable Long id) {
        return developerService.getPortfolioForDeveloper(id).stream().map(PortfolioDto::from).toList();
    }

    @PostMapping("/{id}/portfolio")
    public ResponseEntity<PortfolioDto> addPortfolio(@PathVariable Long id,
                                                     @Valid @RequestBody PortfolioCreateRequest request) {
        PortfolioProject project = developerService.addPortfolioProject(
                id,
                request.title(),
                request.description(),
                request.techStack(),
                request.imageUrl()
        );
        return ResponseEntity.ok(PortfolioDto.from(project));
    }

    public record DeveloperDto(Long id,
                               Long userId,
                               String name,
                               String title,
                               String bio,
                               String skills,
                               String rate,
                               String location,
                               String availability) {
        public static DeveloperDto from(DeveloperProfile profile) {
            Long userId = profile.getUser() == null ? null : profile.getUser().getId();
            return new DeveloperDto(
                    profile.getId(),
                    userId,
                    profile.getName(),
                    profile.getTitle(),
                    profile.getBio(),
                    profile.getSkills(),
                    profile.getRate(),
                    profile.getLocation(),
                    profile.getAvailability()
            );
        }
    }

    public record DeveloperCreateRequest(@NotNull Long userId,
                                         @NotBlank String name,
                                         String title,
                                         String bio,
                                         String skills,
                                         String rate,
                                         String location,
                                         String availability) {
    }

    public record DeveloperUpdateRequest(@NotBlank String name,
                                         String title,
                                         String bio,
                                         String skills,
                                         String rate,
                                         String location,
                                         String availability) {
    }

    public record PortfolioDto(Long id,
                               Long developerId,
                               String title,
                               String description,
                               String techStack,
                               String imageUrl) {
        public static PortfolioDto from(PortfolioProject project) {
            Long devId = project.getDeveloper() == null ? null : project.getDeveloper().getId();
            return new PortfolioDto(
                    project.getId(),
                    devId,
                    project.getTitle(),
                    project.getDescription(),
                    project.getTechStack(),
                    project.getImageUrl()
            );
        }
    }

    public record PortfolioCreateRequest(@NotBlank String title,
                                         String description,
                                         String techStack,
                                         String imageUrl) {
    }
}
