package br.com.medflow.service;

import br.com.medflow.dto.usuarioDTO.UsuarioCadastroDTO;
import br.com.medflow.dto.usuarioDTO.UsuarioRespostaDTO;
import br.com.medflow.entity.Usuario;
import br.com.medflow.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioRespostaDTO cadastrarUsuario(UsuarioCadastroDTO dados) {
        var usuario = new Usuario(dados.email(), passwordEncoder.encode(dados.senha()), dados.perfil());

        var usuarioSalvo = repository.save(usuario);

        return new UsuarioRespostaDTO(usuarioSalvo);
    }


}
