package com.enigov.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Migrates stale role values in MongoDB to the current 2-role model.
 * Runs via @PostConstruct — before any Spring Data repository is initialized,
 * so User documents can be deserialized without hitting unknown enum errors.
 *
 * Converts:
 *   ROLE_ADMINISTRATION -> ROLE_DELEGUE
 *   ROLE_ADMIN          -> ROLE_DELEGUE
 *   ROLE_STUDENT        -> ROLE_ETUDIANT
 */
@Configuration
public class RoleMigration {

    private static final Logger logger = LoggerFactory.getLogger(RoleMigration.class);

    private final MongoClient mongoClient;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    public RoleMigration(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @PostConstruct
    public void migrateRoles() {
        // Extract database name from URI
        String dbName = extractDatabaseName(mongoUri);
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> users = db.getCollection("user");

        migrateRole(users, "ROLE_ADMINISTRATION", "ROLE_DELEGUE");
        migrateRole(users, "ROLE_ADMIN", "ROLE_DELEGUE");
        migrateRole(users, "ROLE_STUDENT", "ROLE_ETUDIANT");
    }

    private void migrateRole(MongoCollection<Document> collection, String oldRole, String newRole) {
        Document filter = new Document("role", oldRole);
        Document update = new Document("$set", new Document("role", newRole));
        UpdateResult result = collection.updateMany(filter, update);
        if (result.getModifiedCount() > 0) {
            logger.info("Migrated {} user(s) from {} -> {}", result.getModifiedCount(), oldRole, newRole);
        }
    }

    private String extractDatabaseName(String uri) {
        // mongodb://localhost:27017/unigov -> unigov
        // mongodb+srv://user:pass@cluster/dbname?... -> dbname
        String withoutProtocol = uri.replaceFirst("mongodb(\\+srv)?://", "");
        String afterSlash = withoutProtocol.contains("/") 
            ? withoutProtocol.substring(withoutProtocol.indexOf("/") + 1) 
            : "test";
        // Remove query params
        if (afterSlash.contains("?")) {
            afterSlash = afterSlash.substring(0, afterSlash.indexOf("?"));
        }
        return afterSlash.isEmpty() ? "test" : afterSlash;
    }
}
