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

public class RabbitMQListenerLog {
    
    /**
     * Permet d'afficher un message d'erreur / debug
     */
    private static final Logger log = LoggerFactory.getLogger(RabbitMQListenerLog.class);
    
    /**
     * Nom du fichier de log. Il peut-être défini à un autre emplacement si besoin
     */
    private static final String logFileName = "projetSDIS.log";
    
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in);
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
