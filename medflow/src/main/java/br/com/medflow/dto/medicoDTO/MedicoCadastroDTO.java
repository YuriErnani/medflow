package br.com.medflow.dto.medicoDTO;

import br.com.medflow.entity.enums.Especialidade;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MedicoCadastroDTO(

        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Size(max = 20)
        String crm,

        @NotNull
        Especialidade especialidade,

        @NotNull
        @Past
        LocalDate dataNascimento

) {}
