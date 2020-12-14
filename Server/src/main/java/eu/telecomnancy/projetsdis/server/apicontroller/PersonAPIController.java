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
    /**
     * Permet d'acc"der au repository de la classe person
     */
    @Autowired
    private CustomerRepository personRepository;
    
    
    /**
     * Route permettant de récupérer l'ensemble des personnes de la base de données
     * @param firstname (Optionnel) Paramètre utilisant le prénom de la personne pour la retrouver
     * @param lastname (Optionnel) Paramètre permettant d'utiliser le nom de la personne pour la retrouver
     * @return List<Person> La liste des personnes répondant à la requête  
     */
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
    
    /**
     * Route GET permerttant de récupérer une personne via son identifiant
     * @param id int Identifiant à utiliser de la personne
     * @return Les informations de la personne ou une erreur si elle n'a pas été trouvée
     * @throws PersonNotFoundException
     */
    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }
    
    /**
     * Route POST permettant de créer une personne dans la base de données
     * @param newPerson Les informations de cette personne
     * @return la personne crée
     */
    @PostMapping("/person/create")
    public Person setPerson(@RequestBody PersonDTO newPerson) {
        Person person = new Person(newPerson.getFirstName(), newPerson.getLastName(), newPerson.getAge());
        person.setTeam(newPerson.getTeam());
        return personRepository.save(person);
    }
    
    /**
     * Route PUT permetant l'édition d'une personne
     * @param newPerson les information de la personne après édition
     * @param id Identifiant de la personne à modifier
     * @return La personne après édition de celle-ci
     */
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
    
    /**
     * Route DELETE permettant de supprimer une personne
     * @param id identifiant de la personne à supprimer
     */
    @DeleteMapping("/person/{id}")
    public void deleteTeam(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
    
    /**
     * Route GET permettant de récuprérer les perosnnes sans équipes
     * @return Les personnes sans équipes
     */
    @GetMapping("/persons/any/team")
    public List<Person> getPersonWithoutTeam() {
        return personRepository.findByAnyTeam();
    }
}
