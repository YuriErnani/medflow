package br.com.medflow.infra.exception;

public class EstadoPacienteInvalidoException extends RuntimeException  {

    public EstadoPacienteInvalidoException(String menssagem) {
        super(menssagem);
    }

}
