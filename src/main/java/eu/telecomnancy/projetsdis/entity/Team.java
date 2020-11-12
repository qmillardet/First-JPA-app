package eu.telecomnancy.projetsdis.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {
    
    private final Date creatation = new Date();
    @OneToMany(mappedBy = "team")
    private final Set<Customer> members;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public Team() {
        this.members = new HashSet<>();
    }
    
    public Date getCreatation() {
        return creatation;
    }
    
    public Long getId() {
        return id;
    }
    
    public boolean addPerson(Customer person) {
        if (this.members.size() < 8) {
            this.members.add(person);
            return true;
        }
        return false;
    }
    
    public boolean isComplete() {
        return this.members.size() >= 8;
    }
}
