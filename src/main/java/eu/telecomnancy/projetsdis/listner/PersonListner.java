package eu.telecomnancy.projetsdis.listner;

import eu.telecomnancy.projetsdis.entity.Person;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@EnableRabbit
public class PersonListner {
    
    @Autowired
    private RabbitTemplate template;
    
    @Autowired
    private FanoutExchange fanout;
    
    @PostPersist
    private void sendMessagePersist(Person person) {
        RabbitMQSendMEssage rb = new RabbitMQSendMEssage("Persist", person);
        sendToRabbitMQ(rb.toJSON());
    }
    
    @PostUpdate
    private void sendMessageUpdate(Person person) {
        RabbitMQSendMEssage rb = new RabbitMQSendMEssage("Update", person);
        sendToRabbitMQ(rb.toJSON());
    }
    
    @PostRemove
    private void sendMessageRemove(Person person) {
        RabbitMQSendMEssage rb = new RabbitMQSendMEssage("Remove", person);
        sendToRabbitMQ(rb.toJSON());
    }
    
    public void sendToRabbitMQ(String message) {
        template.convertAndSend(fanout.getName(), "", message);
    }
    
    private class RabbitMQSendMEssage {
        private final String typeMessage;
        private final Person person;
        
        public RabbitMQSendMEssage(String type, Person person) {
            this.person = person;
            this.typeMessage = type;
        }
        
        public String toJSON() {
            return "{" +
                           "message : \"" + typeMessage + "\"," +
                           "class : \"Person\"," +
                           "id : " + person.getId() +
                           "}";
        }
    }
}
