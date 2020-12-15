# Projet SDIS

Ce projet est à réaliser dans le cadre du cours SDIS de 3ème année à Telecom Nancy

## Auteur

- Quentin Millardet : Élève en apprentissage en 3ème année à Télécom


## Description du projet

Le sujet est disponible dans le fichier [SUJET.md](SUJET.md).

Ce projet est séparé en deux applications Spring boot : 
- La première (Server) correspond au serveur Web contenant l'API web ainsi que la base de données Derby.
- La deuxième (Client) correspond aux clients de log et de monitoring.

## Dependances

### Le code JAVA

Le projet à été réalisé en Java 11 avec le gestionnaire de dépendance Gradle. La compilation de la partie server et de la partie Client sont totalement indépendantes. 
Pour compiler l'une ou l'autre des parties, il suffit de se rendre dans la partie correspondante et de taper la commande : 
- Sous Linux/macOS : ./gradlew build 
- Sous Windows : gradlew.bat build (pas testé au moment de l'écriture du README)

Une liste plus détaillée est disponible dans le README de chaque dossier [Server](Server/README.md) ou [Client](Client/README.md)

### Le script

Le script automatique est basé sur Bash (ne fonctionne pas en zsh). Pour fonctionner, ik necessite que la version de JAVA soit la 11 au minimum. 
Pour utiliser l'interface de test, la lecture des éléments en JSON passe par le parser [jq](https://stedolan.github.io/jq/), il est disponible au téléchargement sur de multiples plateformes : [Télécharger JQ](https://stedolan.github.io/jq/download/)

## Utilisation

Un script bash permet d'automatiser le lancement. Il est disponible avec de nombreuses options : 
- ```--server```     pour compiler et lancer le server automatiquement
- ```--log```        pour compiler et lancer le client de log
- ```--monitoring``` pour compiler et lancer le client de monitoring
- ```--interact```       pour lancer l'interface de test. 
- ```--bulk```       pour lancer le mode Bulk. Il lance automatiquement l'envoie de requêtes (creation de 8 personnes puis ajout d'une équipe et ajout de ces personnes dans l'équipe)
- ```--bulk --launchServer```Il lance automatiquement le serveur, le client de log, le client de monitoring, et l'envoie de requêtes (creation de 8 personnes puis ajout d'une équipe et ajout de ces personnes dans l'équipe). Les fichiers d'affichage des informations sont dans le dossier /tmp/

Exemple de lancement du script : 

    bash launch.sh --bulk --launchServer

Le mode de test est un peu particulier et dispose de son propre interface. La description des fonctions est disponible en tapant la commande "help" dans le prompt. 

L'interface de test permet d'envoyer quelques requêtes simple et n'implémente pas toute l'API.
Les absences : 
- N'affiche pas les membres d'une équipe
- Ne permets pas l'ajout d'un membre à une équipe
- Ne permets pas de visualiser les équipes vides
- Ne permets pas de trouver une personne par son nom
- Ne permets pas de trouver les membres d'une équipe
