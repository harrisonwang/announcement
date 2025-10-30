package com.talkweb.announcement.modules.announcement.service;

import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.UpdatedAnnouncement;

public interface AnnouncementService {
    ExistingAnnouncement createAnnouncement(NewAnnouncement newAnnouncement);

    ExistingAnnouncement getAnnouncementById(Long id);

    ExistingAnnouncement updateAnnouncement(Long id, UpdatedAnnouncement updatedAnnouncement);

    ExistingAnnouncement publishAnnouncement(Long id);
}