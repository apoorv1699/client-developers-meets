package com.cd.platform.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.platform.model.User;
import com.cd.platform.model.UserRole;
import com.cd.platform.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    private final AuthService authService;

    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthService.LoginResult result = authService.login(request.email(), request.password());
        return ResponseEntity.ok(AuthResponse.from(result.user(), result.developerId(), result.clientId()));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        AuthService.SignupResult result = authService.signup(request.toServiceRequest());
        return ResponseEntity.ok(AuthResponse.from(result.user(), result.developerId(), result.clientId()));
    }

    public record AuthResponse(Long userId, UserRole role, String email, Long developerId, Long clientId) {
        public static AuthResponse from(User user, Long developerId, Long clientId) {
            return new AuthResponse(user.getId(), user.getRole(), user.getEmail(), developerId, clientId);
        }
    }

    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {
    }

    public record SignupRequest(@NotNull UserRole role,
                                @NotBlank @Email String email,
                                @NotBlank String password,
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
        public AuthService.SignupRequest toServiceRequest() {
            return new AuthService.SignupRequest(
                    role,
                    email,
                    password,
                    name,
                    title,
                    bio,
                    skills,
                    rate,
                    location,
                    availability,
                    companyName,
                    industry,
                    budgetRange,
                    projectTitle,
                    projectDescription,
                    projectTechStack,
                    projectImageUrl
            );
        }
    }
}
