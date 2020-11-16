package eu.telecomnancy.projetsdis.repository;

import eu.telecomnancy.projetsdis.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    
    List<Team> findByName(String name);
    
    @Query("SELECT t FROM Person p JOIN Team t WHERE count(p.team) > 7")
    List<Team> findByCompleteTeam();
    
    Team findById(long id);
    
    List<Team> findAll();
}
