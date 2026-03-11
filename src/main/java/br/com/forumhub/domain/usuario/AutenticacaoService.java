package br.com.forumhub.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String loginUsuario)
            throws UsernameNotFoundException {

        var usuarioEncontrado = repositorioUsuario.findByEmailAndAtivoTrue(loginUsuario);

        if (usuarioEncontrado == null) {
            throw new UsernameNotFoundException("Usuário com login não encontrado ou inativo");
        }

        return usuarioEncontrado;
    }
}
