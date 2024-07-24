package gabrielleal.recrutamentointerno.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CandidaturaNaoEncontradaException extends RuntimeException {
    public CandidaturaNaoEncontradaException(String message) {
        super(message);
    }
}
