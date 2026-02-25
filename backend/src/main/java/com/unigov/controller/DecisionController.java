package com.unigov.controller;

import com.unigov.entity.Decision;
import com.unigov.repository.DecisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;

@RestController
@RequestMapping("/api/decisions")
public class DecisionController {

    @Autowired
    private DecisionRepository decisionRepository;

    @GetMapping
    public List<Decision> getAllDecisions() {
        return decisionRepository.findAllByOrderByDecisionDateDesc();
    }

    @PostMapping
    @PreAuthorize("hasRole('DELEGUE')")
    public Decision createDecision(@NonNull @RequestBody Decision decision) {
        return decisionRepository.save(decision);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DELEGUE')")
    public ResponseEntity<?> deleteDecision(@NonNull @PathVariable String id) {
        decisionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
