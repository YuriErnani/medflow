package br.com.medflow.repository;

import br.com.medflow.entity.Consulta;
import br.com.medflow.entity.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("""
            SELECT COUNT(c) > 0
            FROM Consulta c
            WHERE c.medico.id = :medicoId
              AND c.status <> :statusCancelada
              AND c.dataHoraInicio < :fim
              AND c.dataHoraInicio > :inicioMenor
            """)
    boolean existeConflitoMedico(
            @Param("medicoId") Long medicoId,
            @Param("inicioMenor") LocalDateTime inicioMenor,
            @Param("fim") LocalDateTime fim,
            @Param("statusCancelada") StatusConsulta statusCancelada
    );

    @Query("""
            SELECT COUNT(c) > 0
            FROM Consulta c
            WHERE c.paciente.id = :pacienteId
              AND c.status <> :statusCancelada
              AND c.dataHoraInicio < :fim
              AND c.dataHoraInicio > :inicioMenor
            """)
    boolean existeConflitoPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("inicioMenor") LocalDateTime inicioMenor,
            @Param("fim") LocalDateTime fim,
            @Param("statusCancelada") StatusConsulta statusCancelada
    );

}