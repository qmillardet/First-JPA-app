package eu.telecomnancy.projetsdis.client;

import java.util.HashMap;
import java.util.Map;

public class Statistiques {
    
    private static final String person = "Person";
    private static final String team = "Team";
    private static Statistiques instance;
    private static Map<String, Integer> valeurs;
    
    private Statistiques() {
    }
    
    public static void addOneToPerson() {
        if (valeurs == null) {
            initialiseArray();
        }
        valeurs.put(person, valeurs.get(person) + 1);
    }
    
    public static void addOneToTeam() {
        if (valeurs == null) {
            initialiseArray();
        }
        valeurs.put(team, Integer.sum(valeurs.get(team), 1));
    }
    
    private static void initialiseArray() {
        valeurs = new HashMap<>();
        valeurs.put(person, 0);
        valeurs.put(team, 0);
    }
    
    /**
     * Méthode permettant de récupérer le nombre de personnes ajoutée dans l'application
     *
     * @return int Nombre de personne dans l'application
     */
    public static int getNumberPerson() {
        if (valeurs == null) {
            initialiseArray();
        }
        return valeurs.get(person);
    }
    
    /**
     * Méthode permettant de récupérer le nombre d'équipes ajoutée dans l'application
     *
     * @return int Nombre d'équipes dans l'application
     */
    public static int getNumberTeam() {
        if (valeurs == null) {
            initialiseArray();
        }
        return valeurs.get(team);
    }
    
    /**
     * Fonction permettant un affichage rapide des statistiques
     *
     * @return String String Contenant l'affichage
     */
    public static String getStatiString() {
        return "Person : " + getNumberPerson() + ", Team : " + getNumberTeam() + ";";
    }
    
    /**
     * Fonction qui permet de supprimer une personne des statistiques
     */
    public static void removeOneToPerson() {
        if (valeurs == null) {
            initialiseArray();
        }
        if (valeurs.get(person) > 1) {
            valeurs.put(person, valeurs.get(person) - 1);
        }
    }
    
    /**
     * Fonction qui permet de supprimer une équipe des statistiques
     */
    public static void removeOneToTeam() {
        if (valeurs == null) {
            initialiseArray();
        }
        if (valeurs.get(team) > 1) {
            valeurs.put(team, valeurs.get(team) - 1);
        }
    }
    
    /**
     * Fonction permettant de vider les données lorsque l'application est arrêtée
     */
    public static void dumpData() {
        initialiseArray();
    }
}
