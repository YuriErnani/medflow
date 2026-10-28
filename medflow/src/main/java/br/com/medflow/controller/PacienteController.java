package br.com.medflow.controller;

import br.com.medflow.dto.pacienteDTO.PacienteAtualizadoDTO;
import br.com.medflow.dto.pacienteDTO.PacienteCadastroDTO;
import br.com.medflow.entity.Paciente;
import br.com.medflow.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paciente")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Paciente>> listarPacientes(@PageableDefault Pageable paginacao) {
        return ResponseEntity
                .ok(service.listarPacientes(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.buscarPacientePorId(id));
    }

    @PostMapping
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody @Valid PacienteCadastroDTO dadosPaciente) {
        var pacienteCadastrado = service.cadastrarPaciente(dadosPaciente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pacienteCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteAtualizadoDTO> atualizarPaciente(@PathVariable Long id, @RequestBody @Valid PacienteAtualizadoDTO dadosPaciente) {
        var pacienteAtualizado = service.atualizarPaciente(id, dadosPaciente);

        return ResponseEntity
                .ok(pacienteAtualizado);
    }

    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        service.desativarPaciente(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarPaciente(@PathVariable Long id) {
        service.ativarPaciente(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
