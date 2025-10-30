package com.talkweb.announcement.modules.announcement.repository;

import com.talkweb.announcement.modules.announcement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a WHERE a.status = 'PUBLISHED' AND a.validTo < :currentDate")
    List<Announcement> findExpiredPublishedAnnouncements(@Param("currentDate") LocalDate currentDate);
}