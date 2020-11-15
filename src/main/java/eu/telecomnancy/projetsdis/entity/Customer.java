package eu.telecomnancy.projetsdis.entity;

import javax.persistence.*;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team team;
    
    protected Customer() {
    }
    
    public Customer(String firstName, String lastName, int age) {
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
    
    public Team getTeam() {
        return team;
    }
    
    public void setTeam(Team team) {
        this.team = team;
    }
}