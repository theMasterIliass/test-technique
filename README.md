# Projet Spring Boot Angular

Ce projet est une application full-stack avec back-end développée avec Spring Boot et front-end avec Angular 20.

## Prérequis

- Java 17 ou supérieur
- Maven 3.8+
- Git

## Installation

1. **Cloner le dépôt :**
    ```bash
    git clone <url-du-repo>
    cd <nom-du-dossier>
    ```

2. **Configurer les variables d'environnement :**
    - Modifier le fichier `src/main/resources/application.properties` selon vos besoins.

3. **Installer les dépendances et compiler :**
    ```bash
    mvn clean install
    ```

## Lancement

```bash
mvn spring-boot:run
```

L'application sera disponible sur [http://localhost:8080](http://localhost:8080).

## Utilisation

- Accédez à l'API via les endpoints documentés dans le projet.
- Pour les tests, utilisez des outils comme Postman ou curl.

## Structure du projet

- `src/main/java` : Code source principal
- `src/main/resources` : Fichiers de configuration
- `src/test/java` : Tests unitaires

## Contribution

Les contributions sont les bienvenues ! Veuillez ouvrir une issue ou une pull request.
