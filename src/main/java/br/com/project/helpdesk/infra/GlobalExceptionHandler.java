package br.com.project.helpdesk.infra;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ChamadoNaoEncontradoException.class)
    public ProblemDetail handleChamadoNaoEncontrado(ChamadoNaoEncontradoException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problemDetail.setTitle("Chamado Não Localizado!");
        problemDetail.setType(URI.create("https://helpdesk.com/erros/chamado-nao-encontrado"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        String erros = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, erros);
        problemDetail.setType(URI.create("https://helpdesk.com/erros/dados-invalidos"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "O corpo da requisição está mal formatado ou contém valores inválidos. Verifique se digitou o Status corretamente.");

        problemDetail.setTitle("Requisição Mal Formatada");
        problemDetail.setType(URI.create("https://helpdesk.com/erros/bad-request"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(status).body(problemDetail);
    }
}
