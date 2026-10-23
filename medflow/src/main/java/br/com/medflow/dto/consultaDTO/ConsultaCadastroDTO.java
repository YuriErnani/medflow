package br.com.medflow.dto.consultaDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaCadastroDTO(

        @NotNull
        Long pacienteId,

        @NotNull
        Long medicoId,

        @NotNull
        @Future
        LocalDateTime dataHoraInicio

) {}
