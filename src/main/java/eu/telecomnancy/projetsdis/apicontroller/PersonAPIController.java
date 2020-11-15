package eu.telecomnancy.projetsdis.apicontroller;

import eu.telecomnancy.projetsdis.entity.Person;
import eu.telecomnancy.projetsdis.exception.EmployeeNotFoundException;
import eu.telecomnancy.projetsdis.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonAPIController {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @GetMapping("/persons")
    public List<Person> getPersons(@RequestParam(value = "firstname", required = false) String firstname, @RequestParam(value = "lastname", required = false) String lastname) {
        if (firstname != null) {
            if (lastname != null) {
                return customerRepository.findByLastNameAndLastName(lastname, firstname);
            }
            return customerRepository.findByFirstName(firstname);
        }
        if (lastname != null) {
            return customerRepository.findByLastName(lastname);
            
        }
        return customerRepository.findAll();
    }
    
    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Long id) throws EmployeeNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
}
