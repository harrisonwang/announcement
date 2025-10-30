package com.talkweb.announcement.modules.announcement.controller;

import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<ExistingAnnouncement> createAnnouncement(@Valid @RequestBody NewAnnouncement newAnnouncement) {
        ExistingAnnouncement existingAnnouncement = announcementService.createAnnouncement(newAnnouncement);
        return ResponseEntity.ok(existingAnnouncement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExistingAnnouncement> getAnnouncementById(@PathVariable Long id) {
        ExistingAnnouncement existingAnnouncement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(existingAnnouncement);
    }
}