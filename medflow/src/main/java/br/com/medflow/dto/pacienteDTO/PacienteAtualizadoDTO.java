package br.com.medflow.dto.pacienteDTO;

import br.com.medflow.entity.Paciente;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PacienteAtualizadoDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        String telefone,

        @NotNull
        @Past
        LocalDate dataNascimento
) {

        public PacienteAtualizadoDTO(Paciente paciente) {
                this(
                        paciente.getNome(),
                        paciente.getEmail(),
                        paciente.getTelefone(),
                        paciente.getDataNascimento());
        }

}
