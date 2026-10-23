package br.com.medflow.infra.exception;

public class ConsultaNaoEncontradaException extends RuntimeException {

    public ConsultaNaoEncontradaException(Long id) {
        super("Consulta não encontrada com o ID " + id);
    }

}
