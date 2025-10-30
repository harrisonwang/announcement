package com.talkweb.announcement.modules.announcement.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.talkweb.announcement.modules.announcement.dto.AnnouncementSearchRequest;
import com.talkweb.announcement.modules.announcement.dto.ExistingAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.NewAnnouncement;
import com.talkweb.announcement.modules.announcement.dto.UpdatedAnnouncement;
import com.talkweb.announcement.modules.announcement.service.AnnouncementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<ExistingAnnouncement> createAnnouncement(
            @Valid @RequestBody NewAnnouncement newAnnouncement) {
        ExistingAnnouncement existingAnnouncement = announcementService.createAnnouncement(newAnnouncement);
        
        URI location = MvcUriComponentsBuilder.fromMethodCall(
                MvcUriComponentsBuilder.on(this.getClass()).getAnnouncementById(existingAnnouncement.id())
        ).build().toUri();
        
        return ResponseEntity.created(location).body(existingAnnouncement);
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
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
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