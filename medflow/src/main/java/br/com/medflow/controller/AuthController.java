package br.com.medflow.controller;

import br.com.medflow.dto.authDTO.LoginDTO;
import br.com.medflow.dto.authDTO.LoginRespostaDTO;
import br.com.medflow.infra.configuration.authentication.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(@RequestBody @Valid LoginDTO dados) {
        var credenciais = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        authenticationManager.authenticate(credenciais);

        var token = jwtService.gerarToken(dados.email());

        return ResponseEntity
                .ok(new LoginRespostaDTO("Login realizado com sucesso", token, "Bearer"));
    }

}
