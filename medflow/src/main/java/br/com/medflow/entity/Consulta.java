package br.com.medflow.entity;

import br.com.medflow.entity.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHoraInicio) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraInicio.plusMinutes(30);
    }

    public void marcarComoCancelada() {
        this.status = StatusConsulta.CANCELADA;
    }

    public void marcarComoRealizada() {
        this.status = StatusConsulta.REALIZADA;
    }

}
