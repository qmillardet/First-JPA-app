# Personne

La gestion des personnes passes par des requêtes GET / POST / PUT / DELETE

## GET

    /persons

Permet de récupérer l'ensemble des personnes de la base de données.

Filtres possibles :


| Nom du pramètre  | Desctiption           | Exemple  |
| :-------------: |:-------------:| :-----: |
| firstname      | Permet de filtrer sur un prénom | ?firstname=quentin |
| lastname      | Permet de filtrer sur un nom de famille      |   ?firstname=millardet |

--------------------------------------------------------------------------------------------

    /person/{id}

Permet de récupérer une personne en connaissant son ID.
{id} est à remplacer par l'identifiant de la personne voulue.

--------------------------------------------------------------------------------------------

    /persons/any/team

Permet de récupérer l'ensemble des personnes sans équipe de la base de données.

## POST

    /person/create

Permet de créer une personne dans la base de données

Exemple de body à envoyer :

    {
        "firstName" : "Quentin",
        "lastName" : "Millardet",
        "age" : 22
    }


## PUT

    /person/{id}

Route permettant d'éditer une personne.
{id} est à remplacer par l'identifiant de la personne voulue.

Exemple de body à envoyer :

    {
        "firstName" : "Metz",
        "lastName" : "Victoire",
        "age" : 101
    }

## DELETE

    /person/{id}

Route permettant de supprimer une personne.
{id} est à remplacer par l'identifiant de la personne voulue.