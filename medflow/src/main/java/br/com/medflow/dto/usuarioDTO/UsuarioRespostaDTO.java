package br.com.medflow.dto.usuarioDTO;

import br.com.medflow.entity.Usuario;
import br.com.medflow.entity.enums.PerfilUsuario;

public record UsuarioRespostaDTO(
        Long id,
        String email,
        PerfilUsuario perfil,
        Boolean ativo
) {

    public UsuarioRespostaDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getAtivo()
        );
    }

}
