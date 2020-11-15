package eu.telecomnancy.projetsdis.apicontroller;

import eu.telecomnancy.projetsdis.entity.Team;
import eu.telecomnancy.projetsdis.exception.EmployeeNotFoundException;
import eu.telecomnancy.projetsdis.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
