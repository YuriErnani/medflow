package br.com.medflow.dto.LoginGitHubDTO;

public record DadosEmailGitHubDTO(
        String email,
        Boolean primary,
        Boolean verified,
        String visibility
) {}
