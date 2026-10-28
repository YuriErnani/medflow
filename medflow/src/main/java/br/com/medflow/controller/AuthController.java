package br.com.medflow.controller;

import br.com.medflow.dto.authDTO.LoginDTO;
import br.com.medflow.dto.authDTO.LoginRespostaDTO;
import br.com.medflow.infra.configuration.authentication.JwtService;
import br.com.medflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(@RequestBody @Valid LoginDTO dados) {
        return ResponseEntity
                .ok(service.login(dados));
    }

}
