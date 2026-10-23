package br.com.medflow.controller;

import br.com.medflow.dto.usuarioDTO.UsuarioCadastroDTO;
import br.com.medflow.dto.usuarioDTO.UsuarioRespostaDTO;
import br.com.medflow.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO dados) {
        service.cadastrarUsuario(dados);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
