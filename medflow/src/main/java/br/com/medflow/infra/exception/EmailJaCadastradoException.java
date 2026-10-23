package br.com.medflow.infra.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("O e-mail " + email + " já está cadastrado");
    }

}
