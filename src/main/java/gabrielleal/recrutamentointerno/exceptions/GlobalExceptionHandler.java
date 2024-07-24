package gabrielleal.recrutamentointerno.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CandidatoNaoEncontradoException.class)
    public ResponseEntity<String> handleCandidatoNaoEncontradoException(CandidatoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CandidaturaNaoEncontradaException.class)
    public ResponseEntity<String> handleCandidaturaNaoEncontradaException(CandidaturaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Outros tratamentos de exceção podem ser adicionados aqui
}
