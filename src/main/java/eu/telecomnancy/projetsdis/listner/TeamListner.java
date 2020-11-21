package eu.telecomnancy.projetsdis.listner;

import eu.telecomnancy.projetsdis.entity.Team;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@EnableRabbit
public class TeamListner {
    
    @Autowired
    private RabbitTemplate template;
    
    @Autowired
    private FanoutExchange fanout;
    
    @PostPersist
    private void sendMessagePersist(Team team) {
        RabbitMQSendMEssageTeam rb = new RabbitMQSendMEssageTeam("Persist", team);
        sendToRabbitMQ(rb.toJSON());
    }
    
    @PostUpdate
    private void sendMessageUpdate(Team team) {
        RabbitMQSendMEssageTeam rb = new RabbitMQSendMEssageTeam("Update", team);
        sendToRabbitMQ(rb.toJSON());
    }
    
    @PostRemove
    private void sendMessageRemove(Team team) {
        RabbitMQSendMEssageTeam rb = new RabbitMQSendMEssageTeam("Remove", team);
        sendToRabbitMQ(rb.toJSON());
    }
    
    public void sendToRabbitMQ(String message) {
        template.convertAndSend(fanout.getName(), "", message);
    }
    
    private class RabbitMQSendMEssageTeam {
        private final String typeMessage;
        private final Team team;
        
        public RabbitMQSendMEssageTeam(String type, Team team) {
            this.team = team;
            this.typeMessage = type;
        }
        
        public String toJSON() {
            return "{" +
                           "message : \"" + this.typeMessage + "\"," +
                           "class : \"Team\"," +
                           "id : " + this.team.getId() +
                           "}";
        }
    }
}
