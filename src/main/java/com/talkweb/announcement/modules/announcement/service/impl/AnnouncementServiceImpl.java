package com.talkweb.announcement.modules.announcement.service.impl;

import com.talkweb.announcement.common.exception.BusinessStateException;
import com.talkweb.announcement.modules.announcement.dto.AnnouncementSearchRequest;
import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.UpdatedAnnouncement;
import com.talkweb.announcement.modules.announcement.entity.Announcement;
import com.talkweb.announcement.modules.announcement.repository.AnnouncementRepository;
import com.talkweb.announcement.modules.announcement.service.AnnouncementService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    private static final Set<String> EDITABLE_STATUSES = Set.of("DRAFT", "REVOKED", "PENDING");

    private static final Set<String> PUBLISHABLE_STATUSES = Set.of("DRAFT", "PENDING", "REVOKED");

    private static final Set<String> REVOKABLE_STATUSES = Set.of("PUBLISHED");

    private static final Set<String> DELETABLE_STATUSES = Set.of("DRAFT", "REVOKED", "PENDING");

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

    @Override
    @Transactional
    public ExistingAnnouncement publishAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("公告不存在，ID: " + id));

        String currentStatus = announcement.getStatus();
        if (!PUBLISHABLE_STATUSES.contains(currentStatus)) {
            throw new BusinessStateException(
                    String.format("当前状态为 %s，只有草稿、待发布或已撤销状态的公告可以发布", currentStatus));
        }

        announcement.setStatus("PUBLISHED");
        announcement.setPublishedAt(LocalDateTime.now());
        announcement.setUpdatedBy("admin");

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return toDTO(savedAnnouncement);
    }

    @Override
    @Transactional
    public ExistingAnnouncement revokeAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("公告不存在，ID: " + id));

        String currentStatus = announcement.getStatus();
        if (!REVOKABLE_STATUSES.contains(currentStatus)) {
            throw new BusinessStateException(
                    String.format("当前状态为 %s，只有已发布状态的公告可以撤销", currentStatus));
        }

        announcement.setStatus("REVOKED");

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return toDTO(savedAnnouncement);
    }

    @Override
    @Transactional
    public ExistingAnnouncement deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("公告不存在，ID: " + id));

        String currentStatus = announcement.getStatus();
        if (!DELETABLE_STATUSES.contains(currentStatus)) {
            throw new BusinessStateException(
                    String.format("当前状态为 %s，只有草稿、已撤销或待发布状态的公告可以删除", currentStatus));
        }

        announcement.setStatus("DELETED");

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return toDTO(savedAnnouncement);
    }

    @Override
    @Transactional
    public int markExpiredAnnouncements() {
        LocalDate currentDate = LocalDate.now();
        List<Announcement> expiredAnnouncements = announcementRepository.findExpiredPublishedAnnouncements(currentDate);

        if (expiredAnnouncements.isEmpty()) {
            log.debug("未找到需要标记为过期的公告");
            return 0;
        }

        expiredAnnouncements.forEach(announcement -> {
            announcement.setStatus("EXPIRED");
            announcement.setUpdatedBy("system");
        });

        announcementRepository.saveAll(expiredAnnouncements);
        log.info("成功标记 {} 条公告为过期状态", expiredAnnouncements.size());

        return expiredAnnouncements.size();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExistingAnnouncement> searchAnnouncements(AnnouncementSearchRequest searchRequest) {
        Specification<Announcement> specification = buildSpecification(searchRequest);
        
        int page = searchRequest.page() != null && searchRequest.page() >= 0 ? searchRequest.page() : 0;
        int size = searchRequest.size() != null && searchRequest.size() > 0 ? searchRequest.size() : 20;
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Announcement> announcementPage = announcementRepository.findAll(specification, pageable);
        
        return announcementPage.map(this::toDTO);
    }

    private Specification<Announcement> buildSpecification(AnnouncementSearchRequest searchRequest) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.title() != null && !searchRequest.title().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), 
                    "%" + searchRequest.title().toLowerCase().trim() + "%"));
            }

            if (searchRequest.status() != null && !searchRequest.status().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("status"), searchRequest.status().trim()));
            }

            if (searchRequest.validFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("validFrom"), searchRequest.validFrom()));
            }

            if (searchRequest.validTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("validTo"), searchRequest.validTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
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