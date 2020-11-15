package eu.telecomnancy.projetsdis.dto;

import eu.telecomnancy.projetsdis.entity.Person;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TeamDTO {
    private final Date creation = new Date();
    private Long id;
    private Set<Person> members = new HashSet<>();
    private String name;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getCreation() {
        return creation;
    }
    
    public Set<Person> getMembers() {
        return members;
    }
    
    public void setMembers(Set<Person> members) {
        this.members = members;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
