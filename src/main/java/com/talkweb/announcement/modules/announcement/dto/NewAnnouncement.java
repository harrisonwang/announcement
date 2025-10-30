package com.talkweb.announcement.modules.announcement.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewAnnouncement(
        @NotBlank(message = "标题不能为空")
        String title,

        @NotBlank(message = "内容不能为空")
        String content,

        @NotNull(message = "生效日期不能为空")
        @FutureOrPresent(message = "生效日期不能小于当前日期")
        LocalDate validFrom,

        @NotNull(message = "失效日期不能为空")
        @Future(message = "失效日期必须大于当前日期")
        LocalDate validTo
) {
}