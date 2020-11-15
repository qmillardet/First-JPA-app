package eu.telecomnancy.projetsdis.repository;

import eu.telecomnancy.projetsdis.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    
    List<Team> findByName(String name);
    
    Team findById(long id);
    
    List<Team> findAll();
}
