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
import com.cd.platform.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequest request) {
        User user = userService.createUser(request.role(), request.email(), request.passwordHash());
        return ResponseEntity.ok(UserDto.from(user));
    }

    public record UserDto(Long id, UserRole role, String email) {
        public static UserDto from(User user) {
            return new UserDto(user.getId(), user.getRole(), user.getEmail());
        }
    }

    public record UserCreateRequest(@NotNull UserRole role,
                                    @NotBlank @Email String email,
                                    @NotBlank String passwordHash) {
    }
}
