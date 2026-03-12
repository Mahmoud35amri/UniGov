package com.enigov;

import com.enigov.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EniGovApplicationTests {

    @Autowired
    private AnnouncementService announcementService;

    @Test
    void contextLoads() {
        assertNotNull(announcementService, "Le contexte Spring devrait charger le service d'annonces.");
    }
}
