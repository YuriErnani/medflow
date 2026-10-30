package br.com.medflow.service;

import br.com.medflow.dto.authDTO.LoginDTO;
import br.com.medflow.dto.authDTO.LoginRespostaDTO;
import br.com.medflow.infra.configuration.authentication.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginRespostaDTO login(LoginDTO dados) {
        var credenciais = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        authenticationManager.authenticate(credenciais);

        var token = jwtService.gerarTokenJWT(dados.email());

        return new LoginRespostaDTO("Login realizado com sucesso", token, "Bearer");
    }
}
