package eu.telecomnancy.projetsdis.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.telecomnancy.projetsdis.client.Statistiques;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class RabbitMQListener {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);
    
    private static final String logFileName = "projetSDIS.log";
    
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receiveBis(in);
    }
    
    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in);
    }
    
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
        } else {
            log.error("Uknown message : " + objet.get("class").toString());
        }
    }
    
    public void receive(String in) {
        
        BufferedWriter writer = null;
        try {
            File idea = new File(logFileName);
            Date date = new Date();
            if (!idea.exists()) {
                writer = new BufferedWriter(new FileWriter(logFileName));
                writer.write("[" + date.toString() + "]" + in);
                writer.newLine();
                writer.close();
            } else {
                writer = new BufferedWriter(new FileWriter(logFileName, true));
                writer.append("[" + date.toString() + "]" + in);
                writer.newLine();
                writer.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
