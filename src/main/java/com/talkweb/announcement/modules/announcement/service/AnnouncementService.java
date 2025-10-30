package com.talkweb.announcement.modules.announcement.service;

import com.talkweb.announcement.modules.announcement.dto.AnnouncementSearchRequest;
import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.UpdatedAnnouncement;
import org.springframework.data.domain.Page;

public interface AnnouncementService {
    ExistingAnnouncement createAnnouncement(NewAnnouncement newAnnouncement);

    ExistingAnnouncement getAnnouncementById(Long id);

    ExistingAnnouncement updateAnnouncement(Long id, UpdatedAnnouncement updatedAnnouncement);

    ExistingAnnouncement publishAnnouncement(Long id);

    ExistingAnnouncement revokeAnnouncement(Long id);

    ExistingAnnouncement deleteAnnouncement(Long id);

    int markExpiredAnnouncements();

    Page<ExistingAnnouncement> searchAnnouncements(AnnouncementSearchRequest searchRequest);
}