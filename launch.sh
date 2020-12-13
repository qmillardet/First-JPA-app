#!/usr/bin/env bash

############################################################
# Script permettant le lancement du server  et des clients #
############################################################

BASEURL="https://localhost:8080"


helpFunc () {
  echo -e "Usage : $0 [--server|--monitor|--log]"
  echo -e ""
  echo -e "    --log \t: Génère l'affichage de log sans accéder à la base de donnée via rabbitMQ"
  echo -e "    --monitor \t: Lance un monitoring temps réel dans la console"
  echo -e "    --server \t: Lance le serveur de l'application"
}


AfficherToutesPersonnes() {
  allData=$(curl -s --location --request GET 'http://localhost:8080/persons' )
  MiseEnFormePersonneAffichage $allData

}

AfficherPersonnesParID() {
  read -p 'Id de la personne recherchée : ' idPersonne
  if [[ $idPerson != "q" ]]; then
    allData=$(curl -s --location --request GET "http://localhost:8080/person/$idPersonne")
    MiseEnFormePersonneAffichage $allData
  fi
}

MiseEnFormePersonnesAffichage() {
    id=( $(jq -r '.[].id' <<< $1) )
    prenom=( $(jq -r '.[].firstName' <<< $1) )
    nom=( $(jq -r '.[].lastName' <<< $1) )
    age=( $(jq -r '.[].age' <<< $1) )
    for ((i = 0 ; i <  ${#id[@]}; i++)); do
      printf 'id : %s, nom : %s, pernom : %s, age : %s\n' "${id[$i]}" "${prenom[$i]}" "${nom[$i]}" "${age[$i]}"
    done
}
MiseEnFormePersonneAffichage() {
    id=( $(jq -r '.id' <<< $1) )
    prenom=( $(jq -r '.firstName' <<< $1) )
    nom=( $(jq -r '.lastName' <<< $1) )
    age=( $(jq -r '.age' <<< $1) )
    for ((i = 0 ; i <  ${#id[@]}; i++)); do
      printf 'id : %s, nom : %s, pernom : %s, age : %s\n' "${id[$i]}" "${prenom[$i]}" "${nom[$i]}" "${age[$i]}"
    done
}

affichageAideTest () {
  echo -e "Bienvenu dans le rpgramme d'execution des requêtes "
  echo -e ""
  echo -e "    ap \t: permet d'afficher l'intégralité des personnes présentes dans l'application dans le format JSON"
  echo -e "    apbyid \t: permet d'afficher une personne à partir de son id"
  echo -e "    --server \t: Lance le serveur de l'application"
}

TestMain() {
 while true; do
   read -p "Entrez votre commande (\"h\" pour l'aide) : " commande
   # case sur une variable d'environnement.

  case $commande in
    h|help) affichageAideTest;;
    ap | afficherpersonne) AfficherToutesPersonnes;;
    apbyid | afficherpersonnebyid) AfficherPersonnesParID;;
    q | quit | exit) exit 0;;
    *) affichageAideTest;;
  esac
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
  else
    helpFunc $0
  fi
else
  helpFunc $0
fi