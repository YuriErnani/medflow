package br.com.medflow.infra.exception;

public class EstadoMedicoInvalidoException extends RuntimeException {

    public EstadoMedicoInvalidoException(String mensagem) {
        super(mensagem);
    }

}
