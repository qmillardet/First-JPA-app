# Equipe

La gestion des Equipes passes par des requêtes GET / POST / PUT / DELETE

## GET

    /teams

Permet de récupérer l'ensemble des équipes de la base de données.

Filtres possibles :


| Nom du pramètre  | Desctiption           | Exemple  |
| :-------------: |:-------------:| :-----: |
| name      | Permet de filtrer sur un nom | ?name=Metz |

--------------------------------------------------------------------------------------------

    /team/{id}

Permet de récupérer une équipe en connaissant son ID.
{id} est à remplacer par l'identifiant de l'équipe voulue.

--------------------------------------------------------------------------------------------

    /team/{id}/members

Permet de récupérer l'ensemble des personnes d'une équipe de la base de données.
{id} est à remplacer par l'identifiant de l'équipe voulue.

--------------------------------------------------------------------------------------------

    /teams/complete

Permet de récupérer l'ensemble des équipes complètes de la base de données.


## POST

    /team/create

Permet de créer une équioe dans la base de données

Exemple de body à envoyer :

    {
        "name" : "Metz"
    }


## PUT

    /team/{id}

Route permettant d'éditer une équipe.
{id} est à remplacer par l'identifiant de l'équipe voulue.

Exemple de body à envoyer :

    {
        "name" : "Saarbrücken"
    }


## DELETE

    /team/{id}

Route permettant de supprimer une équipe.
{id} est à remplacer par l'identifiant de l'équipe à supprimer.