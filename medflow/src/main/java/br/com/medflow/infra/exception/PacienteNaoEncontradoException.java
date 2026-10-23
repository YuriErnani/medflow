package br.com.medflow.infra.exception;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException(Long id) {
        super("Paciente de ID " + id + " não encontrado");
    }

}
