package com.talkweb.announcement.modules.announcement.controller;

import com.talkweb.announcement.modules.announcement.dto.AnnouncementSearchRequest;
import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.UpdatedAnnouncement;
import com.talkweb.announcement.modules.announcement.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<ExistingAnnouncement> createAnnouncement(
            @Valid @RequestBody NewAnnouncement newAnnouncement,
            UriComponentsBuilder uriBuilder) {
        ExistingAnnouncement existingAnnouncement = announcementService.createAnnouncement(newAnnouncement);
        String location = uriBuilder.path("/api/announcements/{id}")
                .buildAndExpand(existingAnnouncement.id())
                .toUriString();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", location)
                .body(existingAnnouncement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExistingAnnouncement> getAnnouncementById(@PathVariable Long id) {
        ExistingAnnouncement existingAnnouncement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(existingAnnouncement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExistingAnnouncement> updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody UpdatedAnnouncement updatedAnnouncement) {
        ExistingAnnouncement existingAnnouncement = announcementService.updateAnnouncement(id, updatedAnnouncement);
        return ResponseEntity.ok(existingAnnouncement);
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<ExistingAnnouncement> publishAnnouncement(@PathVariable Long id) {
        ExistingAnnouncement existingAnnouncement = announcementService.publishAnnouncement(id);
        return ResponseEntity.ok(existingAnnouncement);
    }

    @PutMapping("/{id}/revoke")
    public ResponseEntity<ExistingAnnouncement> revokeAnnouncement(@PathVariable Long id) {
        ExistingAnnouncement existingAnnouncement = announcementService.revokeAnnouncement(id);
        return ResponseEntity.ok(existingAnnouncement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExistingAnnouncement> deleteAnnouncement(@PathVariable Long id) {
        ExistingAnnouncement existingAnnouncement = announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok(existingAnnouncement);
    }

    @GetMapping
    public ResponseEntity<Page<ExistingAnnouncement>> searchAnnouncements(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validTo,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        AnnouncementSearchRequest searchRequest = new AnnouncementSearchRequest(
                title, status, validFrom, validTo, page, size);
        Page<ExistingAnnouncement> result = announcementService.searchAnnouncements(searchRequest);
        return ResponseEntity.ok(result);
    }
}