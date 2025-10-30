package com.talkweb.announcement.modules.announcement.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.talkweb.announcement.modules.announcement.service.AnnouncementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnouncementExpirationJob {

    private final AnnouncementService announcementService;

    @Scheduled(cron = "0 * * * * ?")
    public void markExpiredAnnouncements() {
        log.info("开始执行公告过期检查任务");
        try {
            int count = announcementService.markExpiredAnnouncements();
            log.info("公告过期检查任务完成，共标记 {} 条公告为过期状态", count);
        } catch (Exception e) {
            log.error("执行公告过期检查任务时发生错误", e);
        }
    }
}

