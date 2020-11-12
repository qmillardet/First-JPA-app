package eu.telecomnancy.projetsdis;

import eu.telecomnancy.projetsdis.entity.Customer;
import eu.telecomnancy.projetsdis.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApirestApplication {
    
    private static final Logger log = LoggerFactory.getLogger(ApirestApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(ApirestApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            // save a few customers
            String bauerStr = "Bauer";
            repository.save(new Customer("Jack", bauerStr, 18));
            repository.save(new Customer("Chloe", "O'Brian", 18));
            repository.save(new Customer("Kim", bauerStr, 22));
            repository.save(new Customer("David", "Palmer", 23));
            repository.save(new Customer("Michelle", "Dessler", 110));
    
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
    
            log.info("");
    
            // fetch an individual customer by ID
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info("");
            
            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");
        };
    }
    
}
