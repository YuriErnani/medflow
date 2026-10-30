package br.com.medflow.service;

import br.com.medflow.dto.LoginGitHubDTO.DadosEmailGitHubDTO;
import br.com.medflow.dto.authDTO.LoginRespostaDTO;
import br.com.medflow.infra.configuration.authentication.JwtService;
import br.com.medflow.infra.exception.LoginGitHubNaoAutorizadoException;
import br.com.medflow.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGithHubService {

    private final String redirectUri = "http://localhost:8080/api/v1/login/github/autorizado";

    private final String clientId;
    private final String clientSecret;
    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final RestClient restClient;

    public LoginGithHubService(@Value("${GITHUB_CLIENT_ID}") String clientId, @Value("${GITHUB_CLIENT_SECRET}") String clientSecret, UsuarioRepository repository, JwtService jwtService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.repository = repository;
        this.jwtService = jwtService;
        this.restClient = RestClient.create();
    }

    public String gerarUrl(){
        return "https://github.com/login/oauth/authorize" +
                "?client_id="+ clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=read:user,user:email";
    }

    public LoginRespostaDTO autenticar(String code) {
        var email = obterEmail(code);

        if (email == null)
            throw new LoginGitHubNaoAutorizadoException();

        var usuario = repository.findByEmail(email)
                .orElseThrow(() -> new LoginGitHubNaoAutorizadoException());

        if (!Boolean.TRUE.equals(usuario.getAtivo()))
            throw new LoginGitHubNaoAutorizadoException();

        var token = jwtService.gerarTokenJWT(usuario.getEmail());

        return new LoginRespostaDTO("Login realizado com sucesso", token, "Bearer");
    }

    public String obterEmail(String code) {
        var token = obterToken(code);

        var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var resposta = restClient
                .get()
                .uri("https://api.github.com/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosEmailGitHubDTO[].class);

        for (DadosEmailGitHubDTO dados : resposta) {
            if(dados.primary() && dados.verified())
                return dados.email();
        }

        return null;
    }

    private String obterToken(String code) {
        var resposta = restClient
                .post()
                .uri("https://github.com/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "code", code,
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "redirect_uri", redirectUri))
                .retrieve()
                .body(Map.class);

        var accessToken = resposta.get("access_token");

        if (accessToken == null)
            throw new LoginGitHubNaoAutorizadoException();

        return accessToken.toString();
    }

}
