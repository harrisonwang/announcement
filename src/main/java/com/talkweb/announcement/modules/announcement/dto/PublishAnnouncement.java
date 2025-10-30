package com.talkweb.announcement.modules.announcement.dto;

import jakarta.validation.constraints.NotBlank;

public record PublishAnnouncement(
        @NotBlank(message = "发布人不能为空")
        String publishedBy
) {
}
