package br.com.medflow.controller;

import br.com.medflow.dto.consultaDTO.ConsultaCadastroDTO;
import br.com.medflow.dto.consultaDTO.ConsultaRespostaDTO;
import br.com.medflow.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consulta")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConsultaRespostaDTO> agendarConsulta(@RequestBody @Valid ConsultaCadastroDTO dados) {
        var consulta = service.agendarConsulta(
                dados.pacienteId(),
                dados.medicoId(),
                dados.dataHoraInicio()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(consulta);
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaRespostaDTO>> listarConsultas(Pageable paginacao) {
        return ResponseEntity
                .ok(service.listarConsultas(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaRespostaDTO> buscarConsultaPorId(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.buscarConsultaPorId(id));
    }

    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        service.cancelarConsulta(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/realizar/{id}")
    public ResponseEntity<Void> marcarConsultaComoRealizada(@PathVariable Long id) {
        service.marcarConsultaComoRealizada(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
