# EniGov — Plateforme de Gouvernance Étudiante

> Plateforme numérique de gouvernance interne pour l'École Nationale d'Ingénieurs de Carthage (ENICarthage).

---

## À propos

**EniGov** est une application web full-stack qui centralise la communication entre les étudiants, les délégués et l'administration de l'ENICarthage. Elle permet de gérer les réclamations, les annonces, les sondages, les décisions officielles et la messagerie interne — le tout dans un seul espace sécurisé.

---

## Fonctionnalités

- 🔐 **Authentification** — Inscription avec vérification par email (`@enicar.ucar.tn` uniquement), connexion sécurisée avec JWT, réinitialisation de mot de passe, verrouillage après tentatives échouées
- 👤 **Profil & Paramètres** — Modification du nom, photo de profil (JPG/PNG/WebP, max 5 Mo), changement de mot de passe, thème clair/sombre
- 📢 **Annonces** — Publication et consultation des annonces par les délégués, ciblage par département et année
- 📊 **Sondages** — Création de votes, participation des étudiants, résultats en temps réel
- ⚖️ **Réclamations** — Soumission, suivi et résolution des réclamations étudiantes
- 🏛️ **Décisions Officielles** — Consultation des décisions administratives et académiques
- 💬 **Messagerie** — Communication directe entre étudiants et délégués
- 📋 **Règlement & Procédures** — Accès au règlement intérieur et aux procédures administratives
- 📅 **Agenda** — Suivi des événements et dates importantes

---

## Stack Technique

### Backend
- **Java 17** + **Spring Boot 3**
- **Spring Security** + **JWT** (authentification stateless)
- **MongoDB** (base de données NoSQL)
- **Spring Mail** (vérification email, reset de mot de passe)
- **Maven** (gestion des dépendances)

### Frontend
- **React 18** + **Vite**
- **React Router v6** (navigation)
- **Axios** (client HTTP)
- **Recharts** (graphiques)
- **Framer Motion** (animations)
- **Lucide React** (icônes)
- **Tailwind CSS** (styles)

---

## Rôles

| Rôle | Description |
|------|-------------|
| `ROLE_ETUDIANT` | Étudiant — accès lecture + soumission de réclamations et votes |
| `ROLE_DELEGUE` | Délégué — publication d'annonces, gestion des réclamations, sondages et décisions |

---

## Installation

### Prérequis
- Java 17+
- Node.js 18+
- MongoDB (local ou Atlas)
- Maven

### Backend

```bash
cd backend
cp .env.example .env
# Remplir les variables dans .env
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
cp .env.example .env
# Remplir VITE_API_URL=http://localhost:8080/api
npm install
npm run dev
```

### Variables d'environnement

**Backend (`.env`):**
```
MONGODB_URI=mongodb://localhost:27017/unigov
JWT_SECRET=votre_secret_jwt
MAIL_USERNAME=votre_email@gmail.com
MAIL_PASSWORD=votre_app_password
FILE_UPLOAD_DIR=uploads
FRONTEND_URL=http://localhost:5173
```

**Frontend (`.env`):**
```
VITE_API_URL=http://localhost:8080/api
```

---

## Structure du Projet

```
enigov-platform/
├── backend/                  # Spring Boot API
│   └── src/main/java/com/enigov/
│       ├── controller/       # Endpoints REST
│       ├── entity/           # Entités MongoDB
│       ├── repository/       # Couche données
│       ├── security/         # JWT & Spring Security
│       ├── service/          # Logique métier
│       └── config/           # Configuration
└── frontend/                 # Application React
    └── src/
        ├── pages/            # Pages de l'application
        ├── components/       # Composants réutilisables
        ├── context/          # Auth & Theme context
        └── services/         # Client API (Axios)
```

---

## Sécurité

- JWT avec expiration configurable
- Vérification email obligatoire à l'inscription
- Domaine email restreint (`@enicar.ucar.tn`)
- Verrouillage de compte après 5 tentatives échouées (15 min)
- Validation des uploads (type + taille)
- CORS configuré
- Secrets externalisés via variables d'environnement

---

## Développé par

**Fahem Mouhib** — École Nationale d'Ingénieurs de Carthage, 2026
