package com.enigov.controller;

import com.enigov.dto.AnnouncementDtos.*;
import com.enigov.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @PostMapping(consumes = { "multipart/form-data" })
    @PreAuthorize("hasRole('DELEGUE')")
    public ResponseEntity<AnnouncementResponse> createAnnouncement(@ModelAttribute AnnouncementRequest request,
            Principal principal) {
        return ResponseEntity.ok(announcementService.createAnnouncement(request, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementResponse>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }
}
