package gabrielleal.recrutamentointerno.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CandidatoNaoEncontradoException extends RuntimeException {
    public CandidatoNaoEncontradoException(String message) {
        super(message);
    }
}
