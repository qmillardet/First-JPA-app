package eu.telecomnancy.projetsdis;

import eu.telecomnancy.projetsdis.entity.Person;
import eu.telecomnancy.projetsdis.entity.Team;
import eu.telecomnancy.projetsdis.repository.CustomerRepository;
import eu.telecomnancy.projetsdis.repository.TeamRepository;
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
    public CommandLineRunner demo(CustomerRepository repository, TeamRepository teamrepository) {
        return args -> {
            // save a few customers
            String bauerStr = "Bauer";
            repository.save(new Person("Jack", bauerStr, 18));
            repository.save(new Person("Chloe", "O'Brian", 18));
            repository.save(new Person("Kim", bauerStr, 22));
            repository.save(new Person("David", "Palmer", 23));
            repository.save(new Person("Michelle", "Dessler", 110));
            repository.save(new Person("Camille", "Knauss", 110));
            repository.save(new Person("Jean-Mael", "Itineo", 110));
            repository.save(new Person("Pierre", "Rapido", 110));
            repository.save(new Person("George", "EuraMobile", 110));
            repository.save(new Person("Pierette", "Pilote", 110));
            repository.save(new Person("Jeanne", "Hymmer", 110));
            repository.save(new Person("Jeanne", "Hymmer", 110));
    
            Team teamFr = new Team("Francais");
            teamrepository.save(teamFr);
            teamFr.addMembers(repository.findById(1));
            teamFr.addMembers(repository.findById(2));
            teamFr.addMembers(repository.findById(7));
            teamFr.addMembers(repository.findById(4));
            teamFr.addMembers(repository.findById(3));
            teamFr.addMembers(repository.findById(6));
            teamFr.addMembers(repository.findById(8));
            teamFr.addMembers(repository.findById(5));
            teamFr.addMembers(repository.findById(10));
            teamrepository.save(teamFr);
            
            Team teamDE = new Team("Allemend");
            teamrepository.save(teamDE);
            teamDE.addMembers(repository.findById(11));
            teamDE.addMembers(repository.findById(9));
            teamrepository.save(teamDE);
            
            Team teamJP = new Team("Japonnais");
            teamrepository.save(teamJP);
            
            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            repository.findAll().forEach(bauer -> log.info(bauer.toString()));
            
            log.info("");
            
            // fetch an individual customer by ID
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info("");
            
            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> log.info(bauer.toString()));
            log.info("");
            
            log.info("Teams found with findAll():");
            log.info("--------------------------------------------");
            teamrepository.findAll().forEach(team -> log.info(team.toString()));
        };
    }
    
}
