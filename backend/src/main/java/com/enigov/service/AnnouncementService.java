package com.enigov.service;

import com.enigov.dto.AnnouncementDtos.*;
import com.enigov.entity.Announcement;
import com.enigov.entity.User;
import com.enigov.repository.AnnouncementRepository;
import com.enigov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public AnnouncementResponse createAnnouncement(AnnouncementRequest request, String username) {
        User delegate = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setDelegate(delegate);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            String fileName = fileStorageService.storeFile(request.getFile());
            announcement.setAttachmentUrl(fileName);
        }

        Announcement saved = announcementRepository.save(announcement);
        return mapToResponse(saved);
    }

    public List<AnnouncementResponse> getAllAnnouncements() {
        return announcementRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AnnouncementResponse mapToResponse(Announcement a) {
        return AnnouncementResponse.builder()
                .id(a.getId())
                .title(a.getTitle())
                .content(a.getContent())
                .attachmentUrl(a.getAttachmentUrl())
                .delegateName(a.getDelegate() != null ? a.getDelegate().getFullName() : "Délégué")
                .createdAt(a.getCreatedAt())
                .build();
    }
}
