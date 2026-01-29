# ChatRoom - Application de Discussion en Ligne avec Java RMI

Application de chat en temps réel utilisant Java RMI (Remote Method Invocation) et le service ANT avec une interface graphique Swing.

## Description

Ce projet implémente un système de messagerie instantanée distribué basé sur l'architecture client-serveur. Le serveur gère une salle de discussion où plusieurs clients peuvent se connecter simultanément, envoyer des messages et recevoir les messages des autres utilisateurs en temps réel.

## Architecture

Le projet est organisé en trois packages principaux :

- **`common`** : Interfaces RMI partagées entre le client et le serveur
  - `ChatRoom` : Interface du serveur de chat
  - `ChatUser` : Interface du client de chat

- **`serveur`** : Implémentation du serveur
  - `ChatRoomImpl` : Gestion de la salle de discussion et diffusion des messages

- **`client`** : Implémentation du client
  - `ChatUserImpl` : Interface graphique utilisateur et communication avec le serveur

## Fonctionnalités

-  Connexion multi-utilisateurs simultanée
-  Envoi et réception de messages en temps réel
-  Notifications de connexion/déconnexion des utilisateurs
-  Interface graphique intuitive avec Swing
-  Support de l'envoi de messages par touche Entrée

## Prérequis

- **Java Development Kit (JDK)** : Version 8 ou supérieure
- **Apache Ant** : Pour la compilation et l'exécution du projet

## Installation

1. Clonez le dépôt :
```bash
git clone https://github.com/Lloyd-koutele/Salle-discution-.git
cd Project-Ant-RMI
```

2. Compilez le projet avec Ant :
```bash
ant compile
```

3. (Optionnel) Générez la documentation JavaDoc :
```bash
ant doc
```

4. (Optionnel) Créez les fichiers JAR :
```bash
ant all
```

## Utilisation

### Configuration Réseau

Avant de lancer l'application, vous devez configurer l'adresse IP du serveur :

1. **Dans `ChatRoomImpl.java` (ligne 63)** :
```java
String hostname = "192.168.1.7"; // Remplacez par l'IP de votre serveur
```

2. **Dans `ChatUserImpl.java` (ligne 139)** :
```java
String serverAddress = "192.168.1.7"; // Remplacez par l'IP du serveur
```

### Démarrage du Serveur

**Option 1 : Avec Ant**
```bash
ant run-server
```

**Option 2 : Avec Java**
```bash
java -cp bin -Djava.rmi.server.hostname=<IP_SERVEUR> serveur.ChatRoomImpl
```

**Option 3 : Avec le JAR**
```bash
java -jar archive/ChatServer.jar
```

Le serveur démarrera sur le port **1099** (port par défaut RMI).

### Démarrage du Client

**Option 1 : Avec Ant**
```bash
ant run-client
```

**Option 2 : Avec Java**
```bash
java -cp bin client.ChatUserImpl
```

**Option 3 : Avec le JAR**
```bash
java -jar archive/ChatClient.jar
```

Une fenêtre s'ouvrira vous demandant d'entrer votre pseudo. Après validation, vous pourrez commencer à discuter !

## Commandes Ant Disponibles

| Commande | Description |
|----------|-------------|
| `ant compile` | Compile les sources Java |
| `ant clean` | Supprime les fichiers compilés et générés |
| `ant doc` | Génère la documentation JavaDoc |
| `ant archive` | Crée un fichier JAR général |
| `ant server-jar` | Crée le JAR du serveur uniquement |
| `ant client-jar` | Crée le JAR du client uniquement |
| `ant all` | Nettoie, compile, génère la doc et crée tous les JARs |
| `ant run-server` | Lance le serveur |
| `ant run-client` | Lance le client |

## Structure du Projet

```
Project-Ant-RMI/
├── src/
│   ├── common/
│   │   ├── ChatRoom.java      # Interface RMI du serveur
│   │   └── ChatUser.java      # Interface RMI du client
│   ├── serveur/
│   │   └── ChatRoomImpl.java  # Implémentation du serveur
│   └── client/
│       └── ChatUserImpl.java  # Implémentation du client
├── bin/                        # Fichiers compilés (.class)
├── doc/                        # Documentation JavaDoc
├── archive/                    # Fichiers JAR
│   ├── ChatRoom.jar
│   ├── ChatServer.jar
│   └── ChatClient.jar
├── build.xml                   # Script de build Ant
└── README.md                   # Ce fichier
```

## Auteur (Lloyd Marvin KOUTELE)

Projet réalisé dans le cadre du Master 2 GLSI - Services Web

##  Contribution

Les contributions sont les bienvenues ! N'hésitez pas à :
- Signaler des bugs
- Proposer de nouvelles fonctionnalités
- Améliorer la documentation

---

**Bon chat !**
