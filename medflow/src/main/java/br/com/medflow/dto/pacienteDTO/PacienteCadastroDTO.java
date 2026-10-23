package br.com.medflow.dto.pacienteDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PacienteCadastroDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @Email
        @NotBlank
        @Size(max = 150)
        String email,

        @NotBlank
        String telefone,

        @NotNull
        @Past
        LocalDate dataNascimento
) {}
