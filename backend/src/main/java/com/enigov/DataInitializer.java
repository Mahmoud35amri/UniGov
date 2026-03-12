package com.enigov;

import com.enigov.entity.Role;
import com.enigov.entity.User;
import com.enigov.repository.UserRepository;
import com.enigov.repository.ComplaintRepository;
import com.enigov.repository.PollRepository;
import com.enigov.repository.EventRepository;
import com.enigov.repository.DecisionRepository;
import com.enigov.repository.PollOptionRepository;
import com.enigov.repository.AnnouncementRepository;
import com.enigov.entity.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

import org.springframework.core.annotation.Order;

@Component
@Order(2)
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final PollRepository pollRepository;
    private final EventRepository eventRepository;
    private final DecisionRepository decisionRepository;
    private final PollOptionRepository pollOptionRepository;
    private final AnnouncementRepository announcementRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
            ComplaintRepository complaintRepository,
            PollRepository pollRepository,
            EventRepository eventRepository,
            DecisionRepository decisionRepository,
            PollOptionRepository pollOptionRepository,
            AnnouncementRepository announcementRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
        this.pollRepository = pollRepository;
        this.eventRepository = eventRepository;
        this.decisionRepository = decisionRepository;
        this.pollOptionRepository = pollOptionRepository;
        this.announcementRepository = announcementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedAllData();
    }

    public void seedAllData() {
        try {
            doSeedAllData();
        } catch (Exception e) {
            logger.error("Data initialization failed (possibly stale data in MongoDB). " +
                    "Consider dropping the database and restarting. Error: {}", e.getMessage());
        }
    }

    private void doSeedAllData() {
        // 1. Initialize Users
        logger.info("Checking for default users...");

        // Admin
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@enigov.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setRole(Role.ROLE_DELEGUE);
            admin.setEmailVerified(true);
            userRepository.save(admin);
            logger.info("Admin account created.");
        }

        // Delegate 1: Mouhib Fahem
        if (userRepository.findByUsername("mouhib_fahem").isEmpty()) {
            User delegue1 = new User();
            delegue1.setUsername("mouhib_fahem");
            delegue1.setEmail("mouhib.fahem28@gmail.com");
            delegue1.setPassword(passwordEncoder.encode("mouhib"));
            delegue1.setFullName("Mouhib Fahem");
            delegue1.setRole(Role.ROLE_DELEGUE);
            delegue1.setEmailVerified(true);
            userRepository.save(delegue1);
            logger.info("Delegate 'mouhib_fahem' created.");
        }

        // Delegate 2: Wiem Tamboura
        if (userRepository.findByUsername("wiem_tamboura").isEmpty()) {
            User delegue2 = new User();
            delegue2.setUsername("wiem_tamboura");
            delegue2.setEmail("wiem.tamboura@enigov.com");
            delegue2.setPassword(passwordEncoder.encode("wiem"));
            delegue2.setFullName("Wiem Tamboura");
            delegue2.setRole(Role.ROLE_DELEGUE);
            delegue2.setEmailVerified(true);
            userRepository.save(delegue2);
            logger.info("Delegate 'wiem_tamboura' created.");
        }

        // Student
        if (userRepository.findByUsername("etudiant1").isEmpty()) {
            User student = new User();
            student.setUsername("etudiant1");
            student.setEmail("etudiant@enigov.com");
            student.setPassword(passwordEncoder.encode("etudiant123"));
            student.setFullName("Etudiant Amri Mahmoud");
            student.setRole(Role.ROLE_ETUDIANT);
            student.setEmailVerified(true);
            userRepository.save(student);
            logger.info("Student account created.");
        }

        logger.info("=== User Initialization Complete ===");

        // Seed Complaints if none exist
        if (complaintRepository.count() == 0) {
            User student = userRepository.findByUsername("etudiant1").orElse(null);

            if (student != null) {
                // Cafeteria Complaint
                com.enigov.entity.Complaint c1 = new com.enigov.entity.Complaint();
                c1.setTitle("État de la buvette annexe");
                c1.setDescription(
                        "L'état de la buvette de l'annexe est déplorable. Hygiène douteuse et manque de choix.");
                c1.setCategory("Infrastructure");
                c1.setPriority(com.enigov.entity.ComplaintEnums.ComplaintPriority.HIGH);
                c1.setStatus(com.enigov.entity.ComplaintEnums.ComplaintStatus.PENDING);
                c1.setStudent(student);
                complaintRepository.save(c1);

                // Exam Results Complaint
                com.enigov.entity.Complaint c2 = new com.enigov.entity.Complaint();
                c2.setTitle("Retard affichage notes examens");
                c2.setDescription(
                        "Les notes devaient être affichées le 25/02 comme prévu par le règlement. Le retard est inacceptable.");
                c2.setCategory("Exams");
                c2.setPriority(com.enigov.entity.ComplaintEnums.ComplaintPriority.URGENT);
                c2.setStatus(com.enigov.entity.ComplaintEnums.ComplaintStatus.IN_PROGRESS);
                c2.setStudent(student);
                complaintRepository.save(c2);

                logger.info("=== Demo Complaints created ===");
            }
        }

        // Seed Polls
        User delegue = userRepository.findByUsername("mouhib_fahem").orElse(null);
        if (delegue != null) {
            // Poll 1: Revision Week
            String q1 = "Êtes-vous favorables à une semaine de révision supplémentaire avant les rattrapages ?";
            if (pollRepository.findByQuestion(q1).isEmpty()) {
                com.enigov.entity.Poll p1 = new com.enigov.entity.Poll();
                p1.setQuestion(q1);
                p1.setCreator(delegue);
                p1.setOptions(new ArrayList<>());
                pollRepository.save(p1);

                com.enigov.entity.PollOption p1o1 = new com.enigov.entity.PollOption();
                p1o1.setText("Oui, c'est indispensable");
                p1o1.setPoll(p1);
                p1o1.setVotes(45);
                pollOptionRepository.save(p1o1);

                com.enigov.entity.PollOption p1o2 = new com.enigov.entity.PollOption();
                p1o2.setText("Non, je préfère finir plus tôt");
                p1o2.setPoll(p1);
                p1o2.setVotes(12);
                pollOptionRepository.save(p1o2);

                p1.getOptions().add(p1o1);
                p1.getOptions().add(p1o2);
                pollRepository.save(p1);
                logger.info("Poll 'Revision Week' seeded.");
            }

            // Poll 2: Cafeteria Services
            String q2 = "Quel nouveau service souhaiteriez-vous à la buvette annexe ?";
            if (pollRepository.findByQuestion(q2).isEmpty()) {
                com.enigov.entity.Poll p2 = new com.enigov.entity.Poll();
                p2.setQuestion(q2);
                p2.setCreator(delegue);
                p2.setOptions(new ArrayList<>());
                pollRepository.save(p2);

                String[] p2opts = { "Plus de choix de plats", "Paiement mobile", "Micro-ondes", "Autre" };
                for (String opt : p2opts) {
                    com.enigov.entity.PollOption o = new com.enigov.entity.PollOption();
                    o.setText(opt);
                    o.setPoll(p2);
                    o.setVotes((int) (Math.random() * 30));
                    pollOptionRepository.save(o);
                    p2.getOptions().add(o);
                }
                pollRepository.save(p2);
                logger.info("Poll 'Cafeteria' seeded.");
            }

            // Poll 3: Satisfaction
            String q3 = "Globalement, comment jugez-vous la réactivité de l'administration ce semestre ?";
            if (pollRepository.findByQuestion(q3).isEmpty()) {
                com.enigov.entity.Poll p3 = new com.enigov.entity.Poll();
                p3.setQuestion(q3);
                p3.setCreator(delegue);
                p3.setOptions(new ArrayList<>());
                pollRepository.save(p3);

                String[] p3opts = { "Excellente", "Bonne", "Passable", "Mauvaise" };
                for (String opt : p3opts) {
                    com.enigov.entity.PollOption o = new com.enigov.entity.PollOption();
                    o.setText(opt);
                    o.setPoll(p3);
                    o.setVotes((int) (Math.random() * 25));
                    pollOptionRepository.save(o);
                    p3.getOptions().add(o);
                }
                pollRepository.save(p3);
                logger.info("Poll 'Satisfaction' seeded.");
            }
        }

        // Seed Events
        if (eventRepository.count() == 0) {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            eventRepository.save(new com.enigov.entity.Event(
                    "Réunion de Coordination",
                    "Session de travail hebdomadaire entre les délégués et l'administration.",
                    "Bloc A - Salle 102",
                    now.plusDays(1).withHour(14).withMinute(0),
                    now.plusDays(1).withHour(16).withMinute(0),
                    com.enigov.entity.Event.EventType.MEETING));

            eventRepository.save(new com.enigov.entity.Event(
                    "Fin des Inscriptions PFE",
                    "Dernier délai pour soumettre les formulaires de stage et PFE.",
                    "Portail Etudiant",
                    now.plusDays(3).withHour(23).withMinute(59),
                    now.plusDays(3).withHour(23).withMinute(59),
                    com.enigov.entity.Event.EventType.ACADEMIC));

            eventRepository.save(new com.enigov.entity.Event(
                    "Soirée d'intégration",
                    "Événement festif pour accueillir les nouveaux arrivants.",
                    "Club Etudiants",
                    now.plusDays(5).withHour(19).withMinute(0),
                    now.plusDays(6).withHour(0).withMinute(0),
                    com.enigov.entity.Event.EventType.SOCIAL));

            logger.info("=== Events seeded ===");
        }

        // Seed Decisions
        if (decisionRepository.count() == 0) {
            decisionRepository.save(new com.enigov.entity.Decision(
                    "Nouveaux Horaires de la Bibliothèque",
                    "À partir du lundi prochain, la bibliothèque sera ouverte de 08h00 à 20h00 sans interruption.",
                    "Infrastructure"));
            decisionRepository.save(new com.enigov.entity.Decision(
                    "Report des Examens de TP",
                    "Les examens de travaux pratiques prévus pour mardi sont reportés à une date ultérieure.",
                    "Académique"));
            logger.info("=== Decisions seeded ===");
        }

        // Seed Announcements
        if (delegue != null) {
            String t1 = "Bienvenue sur la plateforme EniGov !";
            if (announcementRepository.findAll().stream().noneMatch(a -> a.getTitle().equals(t1))) {
                Announcement a1 = new Announcement();
                a1.setTitle(t1);
                a1.setContent(
                        "Nous sommes ravis de lancer ce nouvel espace de communication pour tous les étudiants. Explorez les sondages et exprimez-vous !");
                a1.setDelegate(delegue);
                announcementRepository.save(a1);
                logger.info("Announcement 'Bienvenue' seeded.");
            }

            String t2 = "Rappel : Inscriptions aux Clubs";
            if (announcementRepository.findAll().stream().noneMatch(a -> a.getTitle().equals(t2))) {
                Announcement a2 = new Announcement();
                a2.setTitle(t2);
                a2.setContent(
                        "Le délai pour s'inscrire aux différents clubs de l'ENICarthage est fixé à vendredi prochain. Ne tardez pas !");
                a2.setDelegate(delegue);
                announcementRepository.save(a2);
                logger.info("Announcement 'Clubs' seeded.");
            }

            String t3 = "Maintenance prévue du Portail";
            if (announcementRepository.findAll().stream().noneMatch(a -> a.getTitle().equals(t3))) {
                Announcement a3 = new Announcement();
                a3.setTitle(t3);
                a3.setContent(
                        "Le portail subira une courte maintenance ce dimanche à 22h pour améliorer les performances du système de messagerie.");
                a3.setDelegate(delegue);
                announcementRepository.save(a3);
                logger.info("Announcement 'Maintenance' seeded.");
            }
        }
    }
}
