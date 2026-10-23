package br.com.medflow.infra.exception;

public class EstadoConsultaInvalidoException extends RuntimeException {

    public EstadoConsultaInvalidoException(String mensagem) {
        super(mensagem);
    }

}
