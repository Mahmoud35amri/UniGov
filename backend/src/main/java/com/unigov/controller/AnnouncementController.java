package com.unigov.controller;

import com.unigov.dto.AnnouncementDtos.*;
import com.unigov.service.AnnouncementService;
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
