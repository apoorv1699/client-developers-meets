package com.cd.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cd.platform.model.PortfolioProject;

public interface PortfolioProjectRepository extends JpaRepository<PortfolioProject, Long> {
    List<PortfolioProject> findByDeveloper_Id(Long developerId);
}
