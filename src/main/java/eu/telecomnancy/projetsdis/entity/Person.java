package eu.telecomnancy.projetsdis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import eu.telecomnancy.projetsdis.listner.PersonListner;

import javax.persistence.*;

@Entity
@EntityListeners(PersonListner.class)
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team team;
    
    public Person() {
    }
    
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    
    @Override
    public String toString() {
        String name = "";
        if (team != null) {
            name = team.getName();
        }
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s', age='%d', team='%s']",
                id, firstName, lastName, age, name);
    }
    
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public void setTeam(Team team) {
        this.team = team;
    }
}