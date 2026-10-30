package br.com.medflow.infra.exception;

public class LoginGitHubNaoAutorizadoException extends RuntimeException {

    public LoginGitHubNaoAutorizadoException() {
        super("Não foi possível autenticar com o GitHub");
    }

}
