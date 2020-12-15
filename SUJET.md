Le TP est à faire par deux et à rendre sous la forme d'un repo sur le Gitlab de l'école. Chaque étape doit donner lieu à une commit de release

1 - Développez une API Rest sur Spring permettant de gérer des personnes et des équipes.

Une personne a un nom, un prénom et un âge.
Une équipe a un nom et une date de création.
Une personne ne peut appartenir qu'à une équipe. Elle peut cependant changer d'équipe.
Une équipe ne peut pas avoir plus de 8 participants. Dans ce cas elle est considérée comme complète.
L'API permettra de manipuler ces ressources en respectant les contraintes.

Les données seront stockées dans un SGDB embarqué Apache Derby (https://db.apache.org/derby/)

2 - L'API doit permettre de retrouver :

Les membres d'une équipe
Les équipe complètes
Les participants sans équipe
3 - L'application doit générer un message dans une queue pub/sub RabbitMQ pour chaque opération de modification (not safe). Vous devrez écrire deux clients qui pourront s'abonner à ces messages

a] un client de log qui enregistre dans un fichier toutes les opérations.
b] un client de monitoring qui affiche un compte du nombre de personnes et d'équipes dans la base (sans faire d'accès à la base).
4 - Vous écrirez un script shell permettant de démarrer le serveur localement, de démarrer le client de log, de démarrer le client de monitoring et de lancer des requêtes de façon continue de manière à tester l'ensemble de façon simple. Le test de votre application doit être possible en clonant votre projet et en lançant ce script en faisant l'hypothèse d'une connection Internet et d'une installation Java (le script devra donc exécuter les tâches de build Maven ou Gradle nécessaire). Il doit être simple de configurer le serveur RabbitMQ utilisé si nécessaire.

Le rendu est fixé au 15 décembre 2020 sous la forme d'un dépôt gitlab sur la plateforme de l'école. Vous prendrez soin de créer votre dépôt dans le groupe sdis2k21 (https://gitlab.telecomnancy.univ-lorraine.fr/sdis2k21/). Ce dépôt doit contenir plusieurs commits correspondants au moins aux releases des différentes étapes. Un TP dont tous les commits ont lieu le même jour ne sera pas corrigé !