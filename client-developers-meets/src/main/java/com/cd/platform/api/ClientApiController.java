package com.cd.platform.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cd.platform.model.ClientProfile;
import com.cd.platform.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientApiController {
    private final ClientService clientService;

    public ClientApiController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ClientDto::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientCreateRequest request) {
        ClientProfile profile = clientService.createClient(
            request.userId(),
                request.companyName(),
                request.industry(),
                request.budgetRange()
        );
        return ResponseEntity.ok(ClientDto.from(profile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id,
                                                  @Valid @RequestBody ClientUpdateRequest request) {
        ClientProfile profile = clientService.updateClient(
                id,
                request.companyName(),
                request.industry(),
                request.budgetRange()
        );
        return ResponseEntity.ok(ClientDto.from(profile));
    }

    public record ClientDto(Long id, Long userId, String companyName, String industry, String budgetRange) {
        public static ClientDto from(ClientProfile profile) {
            Long userId = profile.getUser() == null ? null : profile.getUser().getId();
            return new ClientDto(profile.getId(), userId, profile.getCompanyName(), profile.getIndustry(), profile.getBudgetRange());
        }
    }

    public record ClientCreateRequest(@NotNull Long userId,
                                      @NotBlank String companyName,
                                      String industry,
                                      String budgetRange) {
    }

    public record ClientUpdateRequest(@NotBlank String companyName,
                                      String industry,
                                      String budgetRange) {
    }
}
