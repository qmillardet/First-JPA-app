package eu.telecomnancy.projetsdis.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class RabbitMQListenerMonitor {
    
    /**
     * Permet d'afficher un message d'erreur / debug
     */
    private static final Logger log = LoggerFactory.getLogger(RabbitMQListenerMonitor.class);
    
    
    /**
     *
     * @param in
     * @throws InterruptedException
     */
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receiveBis(in);
    }
    
    /**
     * Fonction permettant de traiter les messages reçus de la file et de mettre à jour les statistiques
     * @param in
     * @throws InterruptedException
     */
    public void receiveBis(String in) throws InterruptedException {
        JsonObject objet = new JsonParser().parse(in).getAsJsonObject();
        
        String classeARegarder = objet.get("class").toString().split("\"")[1];
        String messageARegarder = objet.get("message").toString().split("\"")[1];
        if (classeARegarder.equals("Team")) {
            if (messageARegarder.equals("Persist")) {
                Statistiques.addOneToTeam();
            } else if (messageARegarder.equals("Remove")) {
                Statistiques.removeOneToTeam();
            }
        } else if (classeARegarder.equals("Person")) {
            if (messageARegarder.equals("Persist")) {
                Statistiques.addOneToPerson();
            } else if (messageARegarder.equals("Remove")) {
                Statistiques.removeOneToPerson();
            }
        } else if (classeARegarder.equals("Stop") || classeARegarder.equals("Start")) {
            Statistiques.dumpData();
        } else {
            log.error("Uknown message : " + objet.get("class").toString());
        }
        log.info(Statistiques.getStatiString());
    }
}
