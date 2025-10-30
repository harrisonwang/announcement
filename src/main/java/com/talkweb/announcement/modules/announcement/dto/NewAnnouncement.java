package com.talkweb.announcement.modules.announcement.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record NewAnnouncement(
        @NotBlank(message = "标题不能为空")
        String title,

        @NotBlank(message = "内容不能为空")
        String content,

        LocalDate validFrom,

        LocalDate validTo,

        @NotBlank(message = "创建人不能为空")
        String createdBy
) {
}