package com.cd.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cd.platform.model.DeveloperProfile;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
    Optional<DeveloperProfile> findByUser_Id(Long userId);
}
