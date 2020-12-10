package eu.telecomnancy.projetsdis.server.apicontroller;

import eu.telecomnancy.projetsdis.server.dto.TeamDTO;
import eu.telecomnancy.projetsdis.server.entity.Person;
import eu.telecomnancy.projetsdis.server.entity.Team;
import eu.telecomnancy.projetsdis.server.exception.TeamNotFoundException;
import eu.telecomnancy.projetsdis.server.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class TeamAPIController {
    
    private final TeamRepository teamRepository;
    
    public TeamAPIController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    
    @GetMapping("/teams")
    public List<Team> getTeams(@RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return teamRepository.findByName(name);
        }
        return teamRepository.findAll();
    }
    
    @GetMapping("/team/{id}")
    public Team getTeam(@PathVariable Long id) throws TeamNotFoundException {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
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
    
    @GetMapping("/team/{id}/members")
    public Set<Person> getMembersTeam(@PathVariable Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get().getMembers();
        }
        return new HashSet<>();
    }
    
    @GetMapping("/teams/complete")
    public List<Team> getCompleteTeam() {
        List<Team> team = teamRepository.findAll();
        team.removeIf(t -> !t.isComplete());
        return team;
    }
}
