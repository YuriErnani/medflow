package br.com.medflow.dto.authDTO;

public record LoginRespostaDTO(
        String mensagem,
        String token,
        String tipo
) {}
