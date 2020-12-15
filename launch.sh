#!/usr/bin/env bash

############################################################
# Script permettant le lancement du server  et des clients #
############################################################

BASEURL="http://localhost:8080"


helpFunc () {
  echo -e "Usage : $0 [--server|--monitor|--log]"
  echo -e ""
  echo -e "    --log \t: Génère l'affichage de log sans accéder à la base de donnée via rabbitMQ"
  echo -e "    --monitor \t: Lance un monitoring temps réel dans la console"
  echo -e "    --server \t: Lance le serveur de l'application"
  echo -e "    --test \t: L'interface d'interaction de l'application avec l'API"
  echo -e "    --bulk \t: Le script va créer 8 personnes puis une équipe et va les ajouter dans l'équipe"
}

affichageAideTest () {
  echo -e "Bienvenu dans le programme d'exécution des requêtes "
  echo -e ""
  echo -e "Commandes générales : "
  echo -e ""
  echo -e "    help | h \t: Affiche cette aide"
  echo -e "    quit | q \t: Arrêt du programme"
  echo -e ""
  echo -e "Personne : "
  echo -e ""
  echo -e "    ap \t\t: permet d'afficher l'intégralité des personnes présentes dans l'application dans le format JSON"
  echo -e "    apbyid \t: permet d'afficher une personne à partir de son id"
  echo -e "    addp \t: permet d'ajouter une personne en saisissant ses informations"
  echo -e "    addpb \t: permet d'ajouter une personne en boucle"
  echo -e "    editp \t: permet d'éditer une personne en saisissant ses informations"
  echo -e "    delp \t: permet de supprimer une personne via son identifiant"
  echo -e ""
  echo -e "Équipe : "
  echo -e ""
  echo -e "    at \t\t: permet d'ajouter une équipe en saisissant ses informations"
  echo -e "    atbyid \t: permet d'afficher une équipe à partir de son id"
  echo -e "    addt \t: permet d'ajouter une équipe en saisissant ses informations"
  echo -e "    editt \t: permet d'éditer une équipe en saisissant ses informations"
  echo -e "    delt \t: permet de supprimer une équipe via son identifiant"
}

                              ############
                              # Personne #
                              ############



AfficherToutesPersonnes() {
  allData=$(curl -s --location --request GET "$BASEURL/persons" )
  MiseEnFormePersonnesAffichage $allData
}

AfficherPersonnesParID() {
  read -p 'Id de la personne recherchée : ' idPersonne
  if [[ $idPerson != "q" ]]; then
    allData=$(curl -s --location --request GET "$BASEURL/person/$idPersonne")
    MiseEnFormePersonneAffichage $allData
  fi
}

MiseEnFormePersonnesAffichage() {
    retourLine="\n";
    if [[ $# -ge 2 ]]
    then
      if [[ $2 == "true" ]]
      then
        retourLine=""
      fi
    fi
    id=( $(jq -r '.[].id' <<< $1) )
    prenom=( $(jq -r '.[].firstName' <<< $1) )
    nom=( $(jq -r '.[].lastName' <<< $1) )
    age=( $(jq -r '.[].age' <<< $1) )
    for ((i = 0 ; i <  ${#id[@]}; i++)); do
      printf 'id : %s, nom : %s, pernom : %s, age : %s %s' "${id[$i]}" "${prenom[$i]}" "${nom[$i]}" "${age[$i]}" "$retourLine"
    done
}
MiseEnFormePersonneAffichage() {
  retourLine="\n";
    if [[ $# -ge 2 ]]
    then
      if [[ $2 == "true" ]]
      then
        retourLine=""
      fi
    fi
    id=( $(jq -r '.id' <<< $1) )
    prenom=( $(jq -r '.firstName' <<< $1) )
    nom=( $(jq -r '.lastName' <<< $1) )
    age=( $(jq -r '.age' <<< $1) )
    for ((i = 0 ; i <  ${#id[@]}; i++)); do
      printf 'id : %s, nom : %s, pernom : %s, age : %s %s' "${id[$i]}" "${prenom[$i]}" "${nom[$i]}" "${age[$i]}" "$retourLine"
    done
}



AddPersonne() {
    read -p 'Nom : ' nom
    read -p 'Prenom : ' prenom
    read -p 'Age : ' age
    curl --location --request POST "$BASEURL/person/create" \
--header 'Content-Type: application/json' \
--data-raw "{
    \"firstName\" : \"$nom\",
    \"lastName\" : \"$prenom\",
    \"age\" : $age
  }"
  if [[ $? -eq 0 ]]
  then
      echo "Envoi effectuée"
  else
    echo "Une erreur s'est produite lors de l'ajout"
  fi
}

AddPersonneBoucle() {
    nom=test;
    prenom=ptest;
    age=33;
    i=0;
    while true; do
      curl --location --request POST "$BASEURL/person/create" \
  --header 'Content-Type: application/json' \
  --data-raw "{
      \"firstName\" : \"$nom$i\",
      \"lastName\" : \"$prenom$i\",
      \"age\" : $age
    }"
    if [[ $? -eq 0 ]]
    then
        echo "Envoi effectuée : $i"
    else
      echo "Une erreur s'est produite lors de l'ajout"
    fi
    i=$((i+1))
    age=$((age+1))
    sleep 1;
  done
}

EditPersonne() {
    read -p 'Identifiant de la personne à modifier : ' id
    allData=$(curl -s --location --request GET "$BASEURL/person/$id")
    if [[ $? -eq 0 ]]
    then
      nom=""
      prenom=""
      age=""
      prenomarr=( $(jq -r '.firstName' <<< $allData) )
      nomarr=( $(jq -r '.lastName' <<< $allData) )
      agearr=( $(jq -r '.age' <<< $allData) )
      read -p "Nom (${nomarr[0]}) : " nomTmp
      if [[ ! -z "$nomTmp" ]]
      then
        nom=$nomTmp
      fi
      read -p "Preom (${prenomarr[0]}) : " prenomTmp
      if [[ ! -z "$prenomTmp" ]]
      then
        prenom=$prenomTmp
      fi
      read -p "Age (${agearr[0]}) : " ageTmp
      if [[ ! -z "$ageTmp" ]]
      then
        age=$ageTmp
      fi
      curl --location --request PUT "$BASEURL/person/$id" \
  --header 'Content-Type: application/json' \
  --data-raw "{
      \"firstName\" : \"$prenom\",
      \"lastName\" : \"$nom\",
      \"age\" : $age
  }"
    if [[ $? -eq 0 ]]
    then
        echo "Envoi effectuée"
    else
      echo "Une erreur s'est produite lors de l'ajout"
    fi
  fi
}

DeletePerson() {
  read -p 'Id de la personne à supprimer (q pour quitter) : ' idPersonne
  if [[ $idPerson != "q" ]]; then
    allData=$(curl -s --location --request DELETE "$BASEURL/person/$idPersonne")
    if [[ $? -eq 0 ]]
    then
        echo "Suppression effectuée"
    else
      echo "Une erreur s'est produite lors de la suppression"
    fi
  fi
}

                              ########
                              # Team #
                              ########

AfficherToutesEquipes() {
  allData=$(curl -s --location --request GET "$BASEURL/teams" )
  MiseEnFormeEquipesAffichage $allData
}

AfficherTeamParID() {
  read -p "Id de l'équipe recherchée : " idPersonne
  if [[ $idPerson != "q" ]]; then
    allData=$(curl -s --location --request GET "$BASEURL/team/$idPersonne")
    MiseEnFormeEquipeAffichage $allData
  fi
}

MiseEnFormeEquipesAffichage() {
    idTeam=( $(jq -r '.[].id' <<< $1) )
    creation=( $(jq -r '.[].creation' <<< $1) )
    name=( $(jq -r '.[].name' <<< $1) )
    complete=( $(jq -r '.[].complete' <<< $1) )

    for ((iTeam = 0 ; iTeam <  ${#idTeam[@]}; iTeam++)); do
      printf 'id : %s, creation : %s, nom : %s, ' "${idTeam[$iTeam]}" "${creation[$iTeam]}" "${name[$iTeam]}"
      printf 'complete : %s \n ' "${complete[$iTeam]}"
    done
}
MiseEnFormeEquipeAffichage() {
    idTeam=( $(jq -r '.id' <<< $1) )
    creation=( $(jq -r '.creation' <<< $1) )
    name=( $(jq -r '.name' <<< $1) )
    complete=( $(jq -r '.complete' <<< $1) )

    for ((iTeam = 0 ; iTeam <  ${#idTeam[@]}; iTeam++)); do
      printf 'id : %s, creation : %s, nom : %s, ' "${idTeam[$iTeam]}" "${creation[$iTeam]}" "${name[$iTeam]}"
      printf 'complete : %s \n ' "${complete[$iTeam]}"
    done
}

AddTeam() {
    read -p 'Nom : ' nom
    curl --location --request POST "$BASEURL/team/create" \
--header 'Content-Type: application/json' \
--data-raw "{
    \"name\": \"$nom\"
}"
  if [[ $? -eq 0 ]]
  then
      echo "Envoi effectuée"
  else
    echo "Une erreur s'est produite lors de l'ajout"
  fi
}

EditTeam() {
    read -p "Identifiant de l'équipe à modifier : " id
    allData=$(curl -s --location --request GET "$BASEURL/team/$id")
    if [[ $? -eq 0 ]]
    then
      nom=""
      nomarr=( $(jq -r '.name' <<< $allData) )
      read -p "Nom (${nomarr[0]}) : " nomTmp
      if [[ ! -z "$nomTmp" ]]
      then
        nom=$nomTmp
      fi
      curl --location --request PUT "$BASEURL/team/$id" \
  --header 'Content-Type: application/json' \
  --data-raw "{
      \"name\" : \"$nom\"
  }"
    if [[ $? -eq 0 ]]
    then
        echo "Envoi effectuée"
    else
      echo "Une erreur s'est produite lors de l'ajout"
    fi
  fi
}

DeleteEquipe() {
  read -p "Id de l'équipe à supprimer (q pour quitter) : " idTeam
  if [[ $idTeam != "q" ]]; then
    allData=$(curl -s --location --request DELETE "$BASEURL/team/$idTeam")
    if [[ $? -eq 0 ]]
    then
        echo "Suppression effectuée"
    else
      echo "Une erreur s'est produite lors de la suppression"
    fi
  fi
}

TestMain() {
 while true; do
   read -p "Entrez votre commande (\"h\" pour l'aide) : " commande
   # case sur une variable d'environnement.

  case $commande in
    ap | afficherpersonne) AfficherToutesPersonnes;;
    apbyid | afficherpersonnebyid) AfficherPersonnesParID;;
    addp | addperson) AddPersonne;;
    addpb | addpersonboucle) AddPersonneBoucle;;
    editp | editperson) EditPersonne;;
    delp | deleteperson) DeletePerson;;
    at | affichageteam) AfficherToutesEquipes;;
    atbyid | afficherteamnebyid) AfficherTeamParID;;
    addt | addteam) AddTeam;;
    editt | editteam) EditTeam;;
    delt | deleteteam) DeleteTeam;;
    q | quit | exit) exit 0;;
    *) affichageAideTest;;
  esac
 done
}

unknownCommand() {
  echo "Argument inconnu. Essayez l'argument --help pour voir les commandes disponibles"
  exit 1
}
BulkMode() {
    nom=test;
    prenom=ptest;
    age=33;
    i=0;
    nbpersonne=0;
    teamName=PSG;
    id_array=(0 0 0 0 0 0 0 0);
    while true; do
      if [[ $nbpersonne -lt 8 ]]
        then
        req=$(curl -s --location --request POST "$BASEURL/person/create" \
    --header 'Content-Type: application/json' \
    --data-raw "{
        \"firstName\" : \"$nom$i\",
        \"lastName\" : \"$prenom$i\",
        \"age\" : $age
      }")
      if [[ $? -eq 0 ]]
      then
        echo "Ajout de la personné n°$i effectuée"
        nbpersonne=$((nbpersonne+1))
        id=( $(jq -r '.id' <<< $req) )
        id_array[$nbpersonne]=$id;
        i=$((i+1))
        age=$((age+1))
      else
        echo "Une erreur s'est produite lors de l'ajout"
      fi
    else
      req=$(curl -s --location --request POST "$BASEURL/team/create" \
--header 'Content-Type: application/json' \
--data-raw "{
    \"name\": \"$teamName$i\"
}")
      if [[ $? -eq 0 ]]
        then
            echo "Equipe crée"
        else
          echo "Une erreur s'est produite lors de l'ajout"
        fi
      idTeam=( $(jq -r '.id' <<< $req) )
      for idPerson in "${id_array[@]}"
      do
        res=$(curl -s --location --request PUT "$BASEURL/team/$idTeam/add/person/$idPerson")
        if [[ $? -eq 0 ]]
        then
            echo "Personne d'id : $idPerson associée à l'équipe d'id : $idTeam"
        else
          echo "Une erreur s'est produite lors de l'ajout"
        fi
      done
      nbpersonne=0
    fi
    Sleep 1
  done
}

if [[ $# -ge 1 ]]
then
  if [[ $1 == "--server" ]]
  then
    cd Server
    ./gradlew build
    ./gradlew bootRun
  elif [[ $1 == "--monitor" ]]
  then
    cd Client/
    ./gradlew build
    ./gradlew bootRun --args="--spring.profiles.active=monitor"
  elif [[ $1 == "--log" ]]
  then
    cd Client/
    ./gradlew build
    ./gradlew bootRun --args="--spring.profiles.active=log"
  elif [[ $1 == "--test" ]]
  then
    TestMain
  elif [[ $1 == "--bulk" ]]
  then
    if [[ $# -ge 2 ]]
    then
      if [[ $2 == "--launchServer" ]]
      then
        cd Client
        ./gradlew build
        ./gradlew bootRun --args="--spring.profiles.active=log" > /tmp/projetSDISMillardetClientLog.log &
        ./gradlew build
        ./gradlew bootRun --args="--spring.profiles.active=monitor" > /tmp/projetSDISMillardetClientMonitor.log &
        cd ../Server
        ./gradlew build
        ./gradlew bootRun > /tmp/projetSDISMillardetServer.log &
        BulkMode >/tmp/projetSDISMillardetBulkMode.log &
        tail -f /tmp/projetSDISMillardetClientMonitor.log
      fi

    else
      BulkMode
    fi
  elif [[ $1 == "--help" ]]
  then
    helpFunc $0
  else
    unknownCommand
  fi
else
  unknownArgument
fi