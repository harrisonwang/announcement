package com.talkweb.announcement.modules.announcement.repository;

import com.talkweb.announcement.modules.announcement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}