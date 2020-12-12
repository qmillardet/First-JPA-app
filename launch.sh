#!/usr/bin/env bash

helpFunc () {
  echo -e "Usage : $0 [--server|--monitor|--log]"
  echo -e ""
  echo -e "    --log \t: Génère l'affichage de log sans accéder à la base de donnée via rabbitMQ"
  echo -e "    --monitor \t: Lance un monitoring temps réel dans la console"
  echo -e "    --server \t: Lance le serveur de l'application"
}


if [[ $? -ge 1 ]]
then
  if [[ $1 == "--server" ]]
  then
    cd Server
    ./gradlew build
    ./gradlew bootRun
  elif [[ $1 == "--monitor" ]]
  then
    cd Cient
    ./gradlew build
    ./gradlew bootRun --spring.profiles.active=monitor
  elif [[ $1 == "--log" ]]
  then
    cd Cient
    ./gradlew build
    ./gradlew bootRun --spring.profiles.active=monitor
  else
    helpFunc $0
  fi
else
  helpFunc $0
fi