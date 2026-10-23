package br.com.medflow.service;

import br.com.medflow.dto.pacienteDTO.PacienteAtualizadoDTO;
import br.com.medflow.dto.pacienteDTO.PacienteCadastroDTO;
import br.com.medflow.entity.Paciente;
import br.com.medflow.infra.exception.EmailJaCadastradoException;
import br.com.medflow.infra.exception.EstadoPacienteInvalidoException;
import br.com.medflow.infra.exception.PacienteNaoEncontradoException;
import br.com.medflow.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Page<Paciente> listarPacientes(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao);
    }

    public Paciente buscarPacientePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException(id));
    }

    @Transactional
    public Paciente cadastrarPaciente(PacienteCadastroDTO dadosPaciente) {
        var paciente = new Paciente(dadosPaciente);

        if (repository.existsByEmail(dadosPaciente.email())) throw new EmailJaCadastradoException(dadosPaciente.email());

        return repository.save(paciente);
    }

    @Transactional
    public PacienteAtualizadoDTO atualizarPaciente(Long id, PacienteAtualizadoDTO dadosPaciente) {
        var paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException(id));

        if (!paciente.getAtivo()) throw new EstadoPacienteInvalidoException("Não é possível atualizar um paciente inativo");

        paciente.setNome(dadosPaciente.nome());
        paciente.setEmail(dadosPaciente.email());
        paciente.setTelefone(dadosPaciente.telefone());
        paciente.setDataNascimento(dadosPaciente.dataNascimento());

        return new PacienteAtualizadoDTO(paciente);
    }

    @Transactional
    public void desativarPaciente(Long id) {
        var paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException(id));

        if (!paciente.getAtivo()) throw new EstadoPacienteInvalidoException("O paciente já está inativo");

        paciente.setAtivo(false);
    }

    @Transactional
    public void ativarPaciente(Long id) {
        var paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException(id));

        if (paciente.getAtivo()) throw new EstadoPacienteInvalidoException("O paciente já está ativo");

        paciente.setAtivo(true);
    }

}
