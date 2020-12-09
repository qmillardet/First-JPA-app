package eu.telecomnancy.projetsdis.apicontroller;

import eu.telecomnancy.projetsdis.entity.Statistiques;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StatAPIController {
    
    public StatAPIController() {
    
    }
    
    @GetMapping("/stats")
    public Map<String, Integer> getTeams() {
        Map<String, Integer> donnees = new HashMap<>();
        donnees.put("nbPersone", Statistiques.getNumberPerson());
        donnees.put("nbTeam", Statistiques.getNumberTeam());
        return donnees;
    }
}
