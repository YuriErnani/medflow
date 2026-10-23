package br.com.medflow.infra.exception;

public class MedicoNaoEncontradoException extends RuntimeException {

    public MedicoNaoEncontradoException(Long id) {
        super("Médico não encontrado com o ID " + id);
    }

}
