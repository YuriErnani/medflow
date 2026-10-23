package br.com.medflow.infra.exception;

public class HorarioConsultaIndisponivelException extends RuntimeException {

    public HorarioConsultaIndisponivelException(String mensagem) {
        super(mensagem);
    }

}