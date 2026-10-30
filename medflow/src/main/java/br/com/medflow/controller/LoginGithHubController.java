package br.com.medflow.controller;

import br.com.medflow.dto.authDTO.LoginRespostaDTO;
import br.com.medflow.service.LoginGithHubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/login/github")
public class LoginGithHubController {

    private final LoginGithHubService service;

    public LoginGithHubController(LoginGithHubService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Void> redirecionarGitHub() {
        var headers = new HttpHeaders();
        headers.setLocation(URI.create(service.gerarUrl()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/autorizado")
    public ResponseEntity<LoginRespostaDTO> autenticarUserOAuth(@RequestParam String code) {
        return ResponseEntity
                .ok(service.autenticar(code));
    }

}
