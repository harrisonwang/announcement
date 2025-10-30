package com.talkweb.announcement.modules.announcement.service;

import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;

public interface AnnouncementService {
    ExistingAnnouncement createAnnouncement(NewAnnouncement newAnnouncement);
}