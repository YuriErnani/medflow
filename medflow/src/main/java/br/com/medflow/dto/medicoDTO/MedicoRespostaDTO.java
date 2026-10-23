package br.com.medflow.dto.medicoDTO;

import br.com.medflow.entity.enums.Especialidade;
import br.com.medflow.entity.Medico;

import java.time.LocalDate;

public record MedicoRespostaDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        LocalDate dataNascimento,
        Boolean ativo
) {

    public MedicoRespostaDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getDataNascimento(),
                medico.getAtivo()
        );
    }

}
