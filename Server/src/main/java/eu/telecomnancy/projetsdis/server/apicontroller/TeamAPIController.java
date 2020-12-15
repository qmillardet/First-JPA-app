package eu.telecomnancy.projetsdis.server.apicontroller;

import eu.telecomnancy.projetsdis.server.dto.TeamDTO;
import eu.telecomnancy.projetsdis.server.entity.Person;
import eu.telecomnancy.projetsdis.server.entity.Team;
import eu.telecomnancy.projetsdis.server.exception.TeamNotFoundException;
import eu.telecomnancy.projetsdis.server.repository.TeamRepository;
import eu.telecomnancy.projetsdis.server.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class TeamAPIController {
    
    private final TeamRepository teamRepository;
    private final CustomerRepository customerRepository;
    
    public TeamAPIController(TeamRepository teamRepository, CustomerRepository customerRepository) {
        this.teamRepository = teamRepository;
        this.customerRepository = customerRepository;
    }
    
    /**
     * Route Get permettant de récupérer l'ensemble des équipe ou une equipe en utilisant son nom en option
     * @param name (Optionnel) Nom de l'équipe souhaitée
     * @return Liste des équipes selon les critères souhaités
     */
    @GetMapping("/teams")
    public List<Team> getTeams(@RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return teamRepository.findByName(name);
        }
        return teamRepository.findAll();
    }
    
    /**
     * Route GET permettant de révcupérer une équipe par son identiant
     * @param id Identifiant de l'équipe voulue
     * @return Les informations de l'équipe souhaitée
     * @throws TeamNotFoundException
     */
    @GetMapping("/team/{id}")
    public Team getTeam(@PathVariable Long id) throws TeamNotFoundException {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }
    
    /**
     * Route POST permettant de créer une équipe avec ces informations passée dans la body
     * @param newTeam Informations de l'équipe à créer
     * @return l'équipe nouvellement crée
     */
    @PostMapping("/team/create")
    public Team getTeam(@RequestBody TeamDTO newTeam) {
        Team team = new Team();
        team.setName(newTeam.getName());
        team.setMembers(newTeam.getMembers());
        return teamRepository.save(team);
    }
    
    /**
     * Route PUT permettant d'éditer une équipe
     * @param newTeam informtations
     * @param id
     * @return
     */
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
    
    @PutMapping("/team/{idTeam}/add/person/{idPerson}")

    public Team addMembersTeam(@PathVariable Long idPerson, @PathVariable Long idTeam) {
        Optional<Team> team = teamRepository.findById(idTeam);
        Optional<Person> person = customerRepository.findById(idPerson);
        if (team.isPresent() && person.isPresent()) {
            if (!team.get().isComplete()){
                team.get().addMembers(person.get());
                person.get().setTeam(team.get());
                customerRepository.save(person.get());
            }
            return teamRepository.save(team.get());
        }
        return null;
    }
    
    @PutMapping("/team/{idTeam}/delete/person/{idPerson}")
    
    public Team deleteMembersTeam(@PathVariable Long idPerson, @PathVariable Long idTeam) {
        Optional<Team> team = teamRepository.findById(idTeam);
        Optional<Person> person = customerRepository.findById(idPerson);
        if (team.isPresent() && person.isPresent()) {
            team.get().removeMembers(person.get());
            customerRepository.save(person.get());
            teamRepository.save(team.get());
            return team.get();
        }
        return null;
    }
}
