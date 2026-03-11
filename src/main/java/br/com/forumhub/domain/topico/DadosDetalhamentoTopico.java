package br.com.forumhub.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataDeCriacao,
        String status,
        String autor,
        Long idAutor,
        String curso,
        Long idCurso
) {

    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getAutor().getId(),
                topico.getCurso().getNome(),
                topico.getCurso().getId()
        );
    }
}
