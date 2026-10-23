package br.com.medflow.dto.medicoDTO;

import br.com.medflow.entity.enums.Especialidade;
import br.com.medflow.entity.Medico;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MedicoAtualizadoDTO(

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

) {

    public MedicoAtualizadoDTO(Medico medico) {
        this(
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getDataNascimento());
    }

}
