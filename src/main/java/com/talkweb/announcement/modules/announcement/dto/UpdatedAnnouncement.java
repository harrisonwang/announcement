package com.talkweb.announcement.modules.announcement.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdatedAnnouncement(
        @NotBlank(message = "标题不能为空")
        String title,

        @NotBlank(message = "内容不能为空")
        String content,

        LocalDate validFrom,

        LocalDate validTo
) {
}
