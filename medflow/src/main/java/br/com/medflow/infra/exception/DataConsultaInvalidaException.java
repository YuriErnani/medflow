package br.com.medflow.infra.exception;

public class DataConsultaInvalidaException extends RuntimeException {

    public DataConsultaInvalidaException(String mensagem) {
        super(mensagem);
    }

}