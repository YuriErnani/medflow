package br.com.medflow.entity;

import br.com.medflow.dto.pacienteDTO.PacienteCadastroDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Paciente {

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
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Setter
    @Column(nullable = false)
    private Boolean ativo = true;

    public Paciente(PacienteCadastroDTO pacienteCadastroDTO) {
        this.nome = pacienteCadastroDTO.nome();
        this.email = pacienteCadastroDTO.email();
        this.telefone = pacienteCadastroDTO.telefone();
        this.dataNascimento = pacienteCadastroDTO.dataNascimento();
    }

}
