package eu.telecomnancy.projetsdis.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Team not found")
public class TeamNotFoundException extends Exception {
    public TeamNotFoundException(Long id) {
    }
}
