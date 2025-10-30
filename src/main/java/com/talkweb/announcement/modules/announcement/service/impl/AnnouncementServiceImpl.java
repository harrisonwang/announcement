package com.talkweb.announcement.modules.announcement.service.impl;

import com.talkweb.announcement.common.exception.BusinessStateException;
import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.UpdatedAnnouncement;
import com.talkweb.announcement.modules.announcement.entity.Announcement;
import com.talkweb.announcement.modules.announcement.repository.AnnouncementRepository;
import com.talkweb.announcement.modules.announcement.service.AnnouncementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    private static final Set<String> EDITABLE_STATUSES = Set.of("DRAFT", "REVOKED", "PENDING");

    @Override
    @Transactional
    public ExistingAnnouncement createAnnouncement(NewAnnouncement newAnnouncement) {
        Announcement announcement = toEntity(newAnnouncement);
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return toDTO(savedAnnouncement);
    }

    @Override
    @Transactional(readOnly = true)
    public ExistingAnnouncement getAnnouncementById(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("公告不存在，ID: " + id));
        return toDTO(announcement);
    }

    @Override
    @Transactional
    public ExistingAnnouncement updateAnnouncement(Long id, UpdatedAnnouncement updatedAnnouncement) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("公告不存在，ID: " + id));

        String currentStatus = announcement.getStatus();
        if (!EDITABLE_STATUSES.contains(currentStatus)) {
            throw new BusinessStateException(
                    String.format("当前状态为 %s，只有草稿、已撤销或待发布状态的公告可以编辑", currentStatus));
        }

        announcement.setTitle(updatedAnnouncement.title());
        announcement.setContent(updatedAnnouncement.content());
        announcement.setValidFrom(updatedAnnouncement.validFrom());
        announcement.setValidTo(updatedAnnouncement.validTo());

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return toDTO(savedAnnouncement);
    }

    private Announcement toEntity(NewAnnouncement newAnnouncement) {
        Announcement announcement = new Announcement();
        announcement.setTitle(newAnnouncement.title());
        announcement.setContent(newAnnouncement.content());
        announcement.setValidFrom(newAnnouncement.validFrom());
        announcement.setValidTo(newAnnouncement.validTo());
        announcement.setStatus("DRAFT");
        return announcement;
    }

    private ExistingAnnouncement toDTO(Announcement announcement) {
        return new ExistingAnnouncement(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getValidFrom(),
                announcement.getValidTo(),
                announcement.getStatus(),
                announcement.getCreatedAt(),
                announcement.getCreatedBy(),
                announcement.getUpdatedAt(),
                announcement.getUpdatedBy(),
                announcement.getPublishedAt()
        );
    }
}