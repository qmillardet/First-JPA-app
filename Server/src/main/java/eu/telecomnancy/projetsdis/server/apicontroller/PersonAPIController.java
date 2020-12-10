package eu.telecomnancy.projetsdis.server.apicontroller;

import eu.telecomnancy.projetsdis.server.dto.PersonDTO;
import eu.telecomnancy.projetsdis.server.entity.Person;
import eu.telecomnancy.projetsdis.server.exception.PersonNotFoundException;
import eu.telecomnancy.projetsdis.server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonAPIController {
    
    @Autowired
    private CustomerRepository personRepository;
    
    @GetMapping("/persons")
    public List<Person> getPersons(@RequestParam(value = "firstname", required = false) String firstname, @RequestParam(value = "lastname", required = false) String lastname) {
        if (firstname != null) {
            if (lastname != null) {
                return personRepository.findByLastNameAndLastName(lastname, firstname);
            }
            return personRepository.findByFirstName(firstname);
        }
        if (lastname != null) {
            return personRepository.findByLastName(lastname);
    
        }
        return personRepository.findAll();
    }
    
    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }
    
    @PostMapping("/person/create")
    public Person setPerson(@RequestBody PersonDTO newPerson) {
        Person person = new Person(newPerson.getFirstName(), newPerson.getLastName(), newPerson.getAge());
        person.setTeam(newPerson.getTeam());
        return personRepository.save(person);
    }
    
    @PutMapping("/person/{id}")
    public Person replacePerson(@RequestBody PersonDTO newPerson, @PathVariable Long id) {
        
        return personRepository.findById(id)
                       .map(person -> {
                           person.setFirstName(newPerson.getFirstName());
                           person.setLastName(newPerson.getLastName());
                           person.setAge(newPerson.getAge());
                           person.setTeam(newPerson.getTeam());
                           return personRepository.save(person);
                       })
                       .orElseGet(() -> {
                           Person person = new Person();
                           person.setId(id);
                           person.setFirstName(newPerson.getFirstName());
                           person.setLastName(newPerson.getLastName());
                           person.setAge(newPerson.getAge());
                           person.setTeam(person.getTeam());
                           return personRepository.save(person);
                       });
    }
    
    @DeleteMapping("/person/{id}")
    public void deleteTeam(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
    
    @GetMapping("/persons/any/team")
    public List<Person> getPersonWithoutTeam() {
        return personRepository.findByAnyTeam();
    }
}
