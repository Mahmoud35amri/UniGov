# EniGov — Session Notes (2026-03-12)

## What Changed This Session

### 1. Project Rename: UniGov → EniGov
Renamed the entire project from "UniGov" to "EniGov" across backend, frontend, and documentation. **The MongoDB database name was intentionally kept as `unigov`.**

**Backend changes:**
- All 49 Java files: `package com.unigov` → `package com.enigov` (declarations + imports)
- Physical directories renamed: `com/unigov/` → `com/enigov/` (both main + test)
- `UniGovApplication.java` → `EniGovApplication.java` (file + class name)
- `UniGovApplicationTests.java` → `EniGovApplicationTests.java`
- `application.properties`: `spring.application.name=enigov`, property prefix `enigov.app.*`
- `pom.xml`: groupId `com.enigov`, artifactId `enigov`
- `EmailService.java`: email subjects/templates now say "EniGov"
- `DataInitializer.java`: seed email domains `@enigov.com`, announcement text
- `JwtUtils.java`, `WebSecurityConfig.java`, `WebSocketConfig.java`: `@Value("${enigov.app.*}")`
- `.vscode/launch.json`: mainClass + projectName updated
- `Dockerfile`: jar filename `enigov-0.0.1-SNAPSHOT.jar`

**Frontend changes:**
- `package.json`: name `enigov-frontend`
- `LoginPage.jsx`: "votre espace EniGov"
- `RegisterPage.jsx`: "la plateforme EniGov"

**Documentation updated:**
- `README.md`, `DEPLOYMENT_GUIDE.md`, `DATABASE_ERD.md`, `DEPLOY_BACKEND.md`, `DEPLOY_FRONTEND_VERCEL.md`, `RAPPORT_ARCHITECTURE.md`

---

### 2. Removed ROLE_ADMINISTRATION
The platform now has exactly **2 roles**: `ROLE_DELEGUE` and `ROLE_ETUDIANT`.

**What was removed from the `Role.java` enum:**
- `ROLE_ADMINISTRATION`
- `ROLE_ADMIN`
- `ROLE_STUDENT`

**Files modified:**
- `Role.java` — only `ROLE_ETUDIANT` and `ROLE_DELEGUE` remain
- `MessageService.java` — students can only message delegates (removed ADMINISTRATION filter)
- `ComplaintController.java` — `@PreAuthorize` fixed: `hasAnyRole('DELEGUE','ADMIN')` → `hasRole('DELEGUE')`, `hasRole('STUDENT')` → `hasRole('ETUDIANT')`
- `AnnouncementService.java` — fallback label changed from "Admin" to "Délégué"
- `App.jsx` — `/admin-stats` route guard: `['ROLE_DELEGUE']` only
- `SettingsPage.jsx` — role display simplified to Délégué / Étudiant
- `README.md` — roles table updated

---

### 3. Rewrote WebSecurityConfig.java
Complete rewrite with explicit 2-role security rules:

| Endpoint | ETUDIANT | DELEGUE |
|----------|----------|---------|
| `POST /api/auth/**` | ✅ Public | ✅ Public |
| `GET` announcements, events, regulations, polls, decisions | ✅ Read | ✅ Full |
| `POST /api/complaints`, `GET /api/complaints/my` | ✅ | ✅ |
| `POST /api/polls/*/vote` | ✅ Vote | ✅ |
| `GET/POST /api/messages/**` | ✅ | ✅ |
| `GET/PUT /api/users/me, profile, photo, password` | ✅ | ✅ |
| Write (POST/PUT/DELETE) on content endpoints | ❌ | ✅ |

---

### 4. Added RoleMigration.java (temporary)
Because the MongoDB database still had documents with old role values (`ROLE_ADMIN`, `ROLE_ADMINISTRATION`, `ROLE_STUDENT`), the backend would crash on startup. A migration class was added at `config/RoleMigration.java` that converts stale values using raw `MongoClient` before Spring Data deserializes them.

---

## TODO — Cleanup (pending)

### 1. Drop the MongoDB database
```bash
mongosh --eval "use unigov; db.dropDatabase();"
```
The `DataInitializer` will re-seed fresh data on next startup.

### 2. Delete RoleMigration.java
Once the database is clean, delete:
```
backend/src/main/java/com/enigov/config/RoleMigration.java
```

### 3. Remove @Order(2) from DataInitializer.java
Remove the `@Order(2)` annotation and the `import org.springframework.core.annotation.Order;` line.
