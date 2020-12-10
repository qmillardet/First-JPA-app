package eu.telecomnancy.projetsdis.server.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    
    @Autowired
    private RabbitTemplate template;
    
    @Autowired
    private FanoutExchange fanout;
    
    @Bean
    public Queue hello() {
        return new Queue("hello");
    }
    
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }
}

