package com.enigov.controller;

import com.enigov.dto.ComplaintDtos.*;
import com.enigov.entity.ComplaintEnums.ComplaintPriority;
import com.enigov.service.ComplaintService;
import com.enigov.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(consumes = { "multipart/form-data" })
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<ComplaintResponse> createComplaint(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam(value = "priority", defaultValue = "MEDIUM") ComplaintPriority priority,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Principal principal) {

        ComplaintRequest request = new ComplaintRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setCategory(category);
        request.setPriority(priority);

        String attachmentPath = null;
        if (file != null && !file.isEmpty()) {
            attachmentPath = fileStorageService.storeFile(file);
        }
        return ResponseEntity.ok(complaintService.createComplaint(request, principal.getName(), attachmentPath));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(Principal principal) {
        return ResponseEntity.ok(complaintService.getMyComplaints(principal.getName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('DELEGUE')")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DELEGUE')")
    public ResponseEntity<ComplaintResponse> updateComplaint(@PathVariable String id,
            @RequestBody ComplaintUpdate update) {
        return ResponseEntity.ok(complaintService.updateComplaint(id, update));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DELEGUE')")
    public ResponseEntity<?> deleteComplaint(@PathVariable String id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok().build();
    }
}
