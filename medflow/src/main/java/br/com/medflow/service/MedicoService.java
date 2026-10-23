package br.com.medflow.service;

import br.com.medflow.dto.medicoDTO.MedicoAtualizadoDTO;
import br.com.medflow.dto.medicoDTO.MedicoCadastroDTO;
import br.com.medflow.dto.medicoDTO.MedicoRespostaDTO;
import br.com.medflow.entity.Medico;
import br.com.medflow.infra.exception.CrmJaCadastradoException;
import br.com.medflow.infra.exception.EmailJaCadastradoException;
import br.com.medflow.infra.exception.EstadoMedicoInvalidoException;
import br.com.medflow.infra.exception.MedicoNaoEncontradoException;
import br.com.medflow.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public Page<Medico> listarMedicos(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao);
    }

    public Medico buscarMedicoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(id));
    }

    @Transactional
    public MedicoRespostaDTO cadastrarMedico(MedicoCadastroDTO dadosMedico) {
        if (repository.existsByEmail(dadosMedico.email())) throw new EmailJaCadastradoException(dadosMedico.email());

        if (repository.existsByCrm(dadosMedico.crm())) throw new CrmJaCadastradoException(dadosMedico.crm());

        var medico = new Medico(dadosMedico);
        var medicoSalvo = repository.save(medico);

        return new MedicoRespostaDTO(medicoSalvo);
    }

    @Transactional
    public MedicoRespostaDTO atualizarMedico(Long id, MedicoAtualizadoDTO dadosMedico) {
        var medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(id));

        if (!medico.getAtivo()) throw new EstadoMedicoInvalidoException("Não é possível atualizar um médico inativo");

        if (!medico.getEmail().equals(dadosMedico.email()) && repository.existsByEmail(dadosMedico.email())) throw new EmailJaCadastradoException(dadosMedico.email());

        if (!medico.getCrm().equals(dadosMedico.crm()) && repository.existsByCrm(dadosMedico.crm())) throw new CrmJaCadastradoException(dadosMedico.crm());

        medico.setNome(dadosMedico.nome());
        medico.setEmail(dadosMedico.email());
        medico.setTelefone(dadosMedico.telefone());
        medico.setCrm(dadosMedico.crm());
        medico.setEspecialidade(dadosMedico.especialidade());
        medico.setDataNascimento(dadosMedico.dataNascimento());

        return new MedicoRespostaDTO(medico);
    }

    @Transactional
    public void desativarMedico(Long id) {
        var medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(id));

        if (!medico.getAtivo()) throw new EstadoMedicoInvalidoException("O médico já está inativo");

        medico.setAtivo(false);
    }

    @Transactional
    public void ativarMedico(Long id) {
        var medico = repository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(id));

        if (medico.getAtivo()) throw new EstadoMedicoInvalidoException("O médico já está ativo");

        medico.setAtivo(true);
    }

}
