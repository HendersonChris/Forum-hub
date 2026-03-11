package br.com.forumhub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByMensagemAndTitulo(String mensagemTopico, String tituloTopico);
}
