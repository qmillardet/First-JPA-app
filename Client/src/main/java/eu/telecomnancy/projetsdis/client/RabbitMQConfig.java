package eu.telecomnancy.projetsdis.client;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfig {
    
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }
    
    @Profile("monitor")
    private static class ReceiverConfig {
        
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }
        
        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }
        
        @Bean
        public Binding binding1(FanoutExchange fanout,
                                Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }
        
        @Bean
        public Binding binding2(FanoutExchange fanout,
                                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }
        
        @Bean
        public RabbitMQListenerMonitor receiver() {
            return new RabbitMQListenerMonitor();
        }
    }
    
    @Profile("log")
    private static class ReceiverConfigLog {
        
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }
        
        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }
        
        @Bean
        public Binding binding1(FanoutExchange fanout,
                                Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }
        
        @Bean
        public Binding binding2(FanoutExchange fanout,
                                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }
        
        @Bean
        public RabbitMQListenerLog receiver() {
            return new RabbitMQListenerLog();
        }
    }
}
