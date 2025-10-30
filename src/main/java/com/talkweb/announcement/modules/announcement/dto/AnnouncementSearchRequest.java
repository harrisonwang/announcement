package com.talkweb.announcement.modules.announcement.dto;

import java.time.LocalDate;

public record AnnouncementSearchRequest(
        String title,
        String status,
        LocalDate validFrom,
        LocalDate validTo,
        Integer page,
        Integer size
) {
}

