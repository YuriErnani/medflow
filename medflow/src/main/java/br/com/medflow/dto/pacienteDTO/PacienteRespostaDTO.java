package br.com.medflow.dto.pacienteDTO;

import java.time.LocalDate;

public record PacienteRespostaDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        LocalDate dataNascimento
) {}
