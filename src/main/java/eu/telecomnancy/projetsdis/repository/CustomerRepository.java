package eu.telecomnancy.projetsdis.repository;

import eu.telecomnancy.projetsdis.entity.Person;
import eu.telecomnancy.projetsdis.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Person, Long> {
    
    List<Person> findByLastName(String lastName);
    
    List<Person> findByFirstName(String fistName);
    
    
    @Query("SELECT a FROM Person a WHERE a.lastName = :lastname AND a.firstName = :firstname")
    List<Person> findByLastNameAndLastName(@Param("lastname") String lastName, @Param("firstname") String firstName);
    
    Person findById(long id);
    
    List<Person> findAll();
    
    @Query("SELECT a FROM Person a WHERE a.team is null")
    List<Person> findByAnyTeam();
    
    List<Person> findByTeam(Team byId);
}
