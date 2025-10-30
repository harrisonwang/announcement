package com.talkweb.announcement.modules.announcement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExistingAnnouncement(
        Long id,
        String title,
        String content,
        LocalDate validFrom,
        LocalDate validTo,
        String status,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime publishedAt
) {
}