package br.com.forumhub.infra.exception;

import br.com.forumhub.domain.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CatchErrors {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException excecaoValidacao) {

        var listaErros = excecaoValidacao.getFieldErrors();

        return ResponseEntity.badRequest().body(
                listaErros.stream().map(DadosErroException::new).toList()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity tratarTipoInvalido() {
        return ResponseEntity.badRequest().body("Tipo inválido na requisição.");
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroValidacao(ValidacaoException excecaoValidacao) {
        return ResponseEntity.badRequest().body(excecaoValidacao.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarBodyAusente(HttpMessageNotReadableException excecaoBody) {
        return ResponseEntity.badRequest().body(excecaoBody.getMessage());
    }

    private record DadosErroException(String campo, String mensagem) {

        public DadosErroException(FieldError erroCampo) {
            this(erroCampo.getField(), erroCampo.getDefaultMessage());
        }
    }
}
