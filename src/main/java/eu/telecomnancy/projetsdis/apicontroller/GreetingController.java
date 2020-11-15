package eu.telecomnancy.projetsdis.apicontroller;

import eu.telecomnancy.projetsdis.entity.Customer;
import eu.telecomnancy.projetsdis.entity.Greeting;
import eu.telecomnancy.projetsdis.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }
    
    @GetMapping("/persons")
    public List<Customer> gtPersons() {
        return customerRepository.findAll();
    }
    
    @GetMapping("/person")
    public Customer getPerson(@RequestParam(value = "id") long id) {
        return customerRepository.findById(id);
    }
}