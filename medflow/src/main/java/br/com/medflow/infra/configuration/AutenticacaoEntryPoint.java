package br.com.medflow.infra.configuration;

import br.com.medflow.dto.erroDTO.ErroRespostaDTO;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AutenticacaoEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public AutenticacaoEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        var erro = new ErroRespostaDTO(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Não autorizado",
                "E-mail ou senha inválidos",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getOutputStream(), erro);
    }

}
