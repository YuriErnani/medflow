package br.com.medflow.infra.configuration.inicialization;

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

    private final String email;
    private final String senha;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository repository;

    public AdministradorInicial(@Value("${MEDFLOW_ADMIN_EMAIL:}") String email, @Value("${MEDFLOW_ADMIN_PASSWORD:}") String senha, PasswordEncoder passwordEncoder, UsuarioRepository repository) {
        this.email = email;
        this.senha = senha;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean emailInformado = !this.email.isBlank();
        boolean senhaInformada = !this.senha.isBlank();

        if (!emailInformado && !senhaInformada)
            return;

        if (emailInformado != senhaInformada)
            throw new IllegalStateException("Informe tanto o e-mail quanto a senha do administrador inicial");

        if (repository.findByEmail(this.email.trim()).isPresent())
            return;

        var administrador = new Usuario(this.email.trim(), passwordEncoder.encode(this.senha), PerfilUsuario.ADMINISTRADOR);

        repository.save(administrador);
    }

}
