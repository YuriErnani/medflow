package br.com.medflow.controller;

import br.com.medflow.dto.authDTO.LoginDTO;
import br.com.medflow.dto.authDTO.LoginRespostaDTO;
import br.com.medflow.dto.erroDTO.ErroRespostaDTO;
import br.com.medflow.infra.configuration.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dados, HttpServletRequest request) {
        var credenciais = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        try {
            authenticationManager.authenticate(credenciais);

            var token = jwtService.gerarToken(dados.email());

            return ResponseEntity
                    .ok(new LoginRespostaDTO("Login realizado com sucesso", token, "Bearer"));

        } catch (AuthenticationException exception) {
            var erro = new ErroRespostaDTO(
                    401,
                    "Não autorizado",
                    "E-mail ou senha inválidos",
                    request.getRequestURI(),
                    LocalDateTime.now());

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(erro);

        }
    }

}
