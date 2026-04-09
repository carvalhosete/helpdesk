package br.com.project.helpdesk.infra;

public class ChamadoNaoEncontradoException extends RuntimeException {
    public ChamadoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
