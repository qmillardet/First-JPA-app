package eu.telecomnancy.projetsdis.server.listner;


import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@EnableRabbit
public class RebootListener
{
    
    public static boolean alreadySend = false;
    
    public static String message = "{message : \"Stop\",class : \"Stop\",id : -1}";
    
    
    
}