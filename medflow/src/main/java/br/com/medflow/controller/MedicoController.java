package br.com.medflow.controller;

import br.com.medflow.dto.medicoDTO.MedicoAtualizadoDTO;
import br.com.medflow.dto.medicoDTO.MedicoCadastroDTO;
import br.com.medflow.dto.medicoDTO.MedicoRespostaDTO;
import br.com.medflow.entity.Medico;
import br.com.medflow.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medico")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Medico>> listarMedicos(@PageableDefault Pageable paginacao) {
        return ResponseEntity
                .ok(service.listarMedicos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarMedicoPorId(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.buscarMedicoPorId(id));
    }

    @PostMapping
    public ResponseEntity<MedicoRespostaDTO> cadastrarMedico(@RequestBody @Valid MedicoCadastroDTO dadosMedico) {
        var medicoCadastrado = service.cadastrarMedico(dadosMedico);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(medicoCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoRespostaDTO> atualizarMedico(@PathVariable Long id, @RequestBody @Valid MedicoAtualizadoDTO dadosMedico) {
        var medicoAtualizado = service.atualizarMedico(id, dadosMedico);
        return ResponseEntity
                .ok(medicoAtualizado);
    }

    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarMedico(@PathVariable Long id) {
        service.desativarMedico(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarMedico(@PathVariable Long id) {
        service.ativarMedico(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
