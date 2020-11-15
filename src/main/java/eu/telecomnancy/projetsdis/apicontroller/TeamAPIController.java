package eu.telecomnancy.projetsdis.apicontroller;

import eu.telecomnancy.projetsdis.dto.TeamDTO;
import eu.telecomnancy.projetsdis.entity.Team;
import eu.telecomnancy.projetsdis.exception.EmployeeNotFoundException;
import eu.telecomnancy.projetsdis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamAPIController {
    
    @Autowired
    private TeamRepository teamRepository;
    
    @GetMapping("/teams")
    public List<Team> getTeams(@RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return teamRepository.findByName(name);
        }
        return teamRepository.findAll();
    }
    
    @GetMapping("/team/{id}")
    public Team getTeam(@PathVariable Long id) throws EmployeeNotFoundException {
        return teamRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
    
    @PostMapping("/team/create")
    public Team getTeam(@RequestBody TeamDTO newTeam) {
        Team team = new Team();
        team.setName(newTeam.getName());
        team.setMembers(newTeam.getMembers());
        return teamRepository.save(team);
    }
    
    @PutMapping("/team/{id}")
    public Team replaceTeam(@RequestBody TeamDTO newTeam, @PathVariable Long id) {
        
        return teamRepository.findById(id)
                       .map(team -> {
                           team.setName(newTeam.getName());
                           team.setMembers(newTeam.getMembers());
                           return teamRepository.save(team);
                       })
                       .orElseGet(() -> {
                           Team team = new Team();
                           team.setMembers(newTeam.getMembers());
                           team.setName(newTeam.getName());
                           return teamRepository.save(team);
                       });
    }
    
    @DeleteMapping("/team/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamRepository.deleteById(id);
    }
}
