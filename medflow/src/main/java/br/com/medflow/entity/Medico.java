package br.com.medflow.entity;

import br.com.medflow.dto.medicoDTO.MedicoCadastroDTO;
import br.com.medflow.entity.enums.Especialidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 100)
    private String nome;

    @Setter
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Setter
    @Column(nullable = false)
    private String telefone;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    private String crm;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Especialidade especialidade;

    @Setter
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Setter
    @Column(nullable = false)
    private Boolean ativo = true;

    public Medico(MedicoCadastroDTO medicoCadastroDTO) {
        this.nome = medicoCadastroDTO.nome();
        this.email = medicoCadastroDTO.email();
        this.telefone = medicoCadastroDTO.telefone();
        this.crm = medicoCadastroDTO.crm();
        this.especialidade = medicoCadastroDTO.especialidade();
        this.dataNascimento = medicoCadastroDTO.dataNascimento();
    }

}
