package br.com.medflow.dto.erroDTO;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroValidacaoDTO(
        int status,
        String erro,
        String mensagem,
        String caminho,
        LocalDateTime timestamp,
        Map<String, String> detalhes
) {}
