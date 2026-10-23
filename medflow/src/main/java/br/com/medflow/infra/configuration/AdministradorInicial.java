package br.com.medflow.infra.configuration;

import br.com.medflow.entity.Usuario;
import br.com.medflow.entity.enums.PerfilUsuario;
import br.com.medflow.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdministradorInicial implements ApplicationRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String senha;

    public AdministradorInicial(UsuarioRepository repository, PasswordEncoder passwordEncoder, @Value("${MEDFLOW_ADMIN_EMAIL:}") String email, @Value("${MEDFLOW_ADMIN_PASSWORD:}") String senha) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void run(ApplicationArguments args) {

        boolean emailInformado = !email.isBlank();
        boolean senhaInformada = !senha.isBlank();

        if (!emailInformado && !senhaInformada) return;

        if (emailInformado != senhaInformada) throw new IllegalStateException("Informe o e-mail e a senha do administrador inicial");

        if (repository.findByEmail(email.trim()).isPresent()) return;

        var administrador = new Usuario(email.trim(), passwordEncoder.encode(senha), PerfilUsuario.ADMINISTRADOR);

        repository.save(administrador);
    }

}
