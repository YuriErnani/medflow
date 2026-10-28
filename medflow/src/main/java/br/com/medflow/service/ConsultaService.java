package br.com.medflow.service;

import br.com.medflow.dto.consultaDTO.ConsultaRespostaDTO;
import br.com.medflow.entity.Consulta;
import br.com.medflow.entity.enums.StatusConsulta;
import br.com.medflow.infra.exception.*;
import br.com.medflow.repository.ConsultaRepository;
import br.com.medflow.repository.MedicoRepository;
import br.com.medflow.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public ConsultaRespostaDTO agendarConsulta(Long pacienteId, Long medicoId, LocalDateTime dataHoraInicio) {
        var paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNaoEncontradoException(pacienteId));

        var medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new MedicoNaoEncontradoException(medicoId));

        if (!paciente.getAtivo())
            throw new HorarioConsultaIndisponivelException("Não é possível agendar uma consulta para um paciente inativo");

        if (!medico.getAtivo())
            throw new HorarioConsultaIndisponivelException("Não é possível agendar uma consulta com um médico inativo");

        if (dataHoraInicio == null || !dataHoraInicio.isAfter(LocalDateTime.now()))
            throw new DataConsultaInvalidaException("A data e hora da consulta devem estar no futuro");

        var inicioMenor = dataHoraInicio.minusMinutes(30);

        var fim = dataHoraInicio.plusMinutes(30);

        boolean conflitoMedico = consultaRepository.existeConflitoMedico(medicoId, inicioMenor, fim, StatusConsulta.CANCELADA);

        if (conflitoMedico)
            throw new HorarioConsultaIndisponivelException("O médico já possui uma consulta nesse intervalo");

        boolean conflitoPaciente = consultaRepository.existeConflitoPaciente(pacienteId, inicioMenor, fim, StatusConsulta.CANCELADA);

        if (conflitoPaciente)
            throw new HorarioConsultaIndisponivelException("O paciente já possui uma consulta nesse intervalo");

        var consulta = new Consulta(paciente, medico, dataHoraInicio);

        var consultaSalva = consultaRepository.save(consulta);

        return new ConsultaRespostaDTO(consultaSalva);
    }

    @Transactional(readOnly = true)
    public Page<ConsultaRespostaDTO> listarConsultas(Pageable paginacao) {
        return consultaRepository.findAll(paginacao)
                .map(ConsultaRespostaDTO::new);
    }

    @Transactional(readOnly = true)
    public ConsultaRespostaDTO buscarConsultaPorId(Long id) {
        var consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));

        return new ConsultaRespostaDTO(consulta);
    }

    @Transactional
    public void cancelarConsulta(Long id) {
        var consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));

        if (consulta.getStatus() != StatusConsulta.AGENDADA)
            throw new EstadoConsultaInvalidoException("Somente consultas agendadas podem ser canceladas");

        consulta.marcarComoCancelada();
    }

    @Transactional
    public void marcarConsultaComoRealizada(Long id) {
        var consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));

        if (consulta.getStatus() != StatusConsulta.AGENDADA)
            throw new EstadoConsultaInvalidoException("Somente consultas agendadas podem ser marcadas como realizadas");

        consulta.marcarComoRealizada();
    }

}
