package br.com.forumhub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByIdAndAtivoTrue(Long idUsuario);

    UserDetails findByEmailAndAtivoTrue(String emailLogin);

    boolean existsByEmail(String emailUsuario);
}