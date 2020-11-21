package eu.telecomnancy.projetsdis.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import eu.telecomnancy.projetsdis.listner.TeamListner;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(TeamListner.class)
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final Date creation = new Date();
    @JsonManagedReference
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Person> members = new HashSet<>();
    private String name;
    
    
    public Team(String name) {
        this.name = name;
    }
    
    public Team() {
    }
    
    public Date getCreation() {
        return creation;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void addMembers(Person person) {
        if (this.members.size() < 8) {
            this.members.add(person);
            person.setTeam(this);
        }
    }
    
    public boolean removeMembers(Person person) {
        person.setTeam(null);
        return true;
    }
    
    public boolean isComplete() {
        return this.members.size() >= 8;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Set<Person> getMembers() {
        return members;
    }
    
    public void addArrayMembers(Person[] arrayOfMember) {
        Collections.addAll(this.members, arrayOfMember);
    }
    
    public void setMembers(Set<Person> setOfPerson) {
        this.members = setOfPerson;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        this.members.forEach((Person cust) -> str.append("{" + cust.toString() + "}"));
        str.append("}");
        return "Team{" +
                       "creatation=" + creation +
                       ", name='" + name + '\'' +
                       ", members=" + str +
                       ", id=" + id +
                       '}';
    }
}
