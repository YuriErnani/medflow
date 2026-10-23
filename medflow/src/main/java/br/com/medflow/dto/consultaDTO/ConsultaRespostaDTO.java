package br.com.medflow.dto.consultaDTO;

import br.com.medflow.entity.Consulta;
import br.com.medflow.entity.enums.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaRespostaDTO(
        Long id,
        Long pacienteId,
        String pacienteNome,
        Long medicoId,
        String medicoNome,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        StatusConsulta status
) {

    public ConsultaRespostaDTO(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getId(),
                consulta.getMedico().getNome(),
                consulta.getDataHoraInicio(),
                consulta.getDataHoraFim(),
                consulta.getStatus()
        );
    }

}
