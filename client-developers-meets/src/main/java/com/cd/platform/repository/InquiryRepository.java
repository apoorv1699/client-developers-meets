package com.cd.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cd.platform.model.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
