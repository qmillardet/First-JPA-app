#Client

## Dépendances 

Le client nécessite la version 11 de Java ou supérieur.
La gestion des dépendances passe par Gradle. Le script de lancement de ce dernier est dans le dépôt.

Il est nécessaire de posséder une file RabbitMQ pour lire les messages du serveurs. 
La communication se fait par ce moyen.

## Configuration

Il est possible de changer la file rabbitMQ en modifiant le fichier : [src/main/resources/application.yml](src/main/resources/application.yml)

## Utilisation

Pour être lancé, il nécessite de passer par gradle.

Il y a deux modes de fonctionnement : 

- Log: ```./gradlew bootRun --args="--spring.profiles.active=log"```
- Moniteur : ```./gradlew bootRun --args="--spring.profiles.active=monitor"```

(Optionnel) 
Il est possible de compiler l'application indépendamment via la commande :

    ./gradlew build
