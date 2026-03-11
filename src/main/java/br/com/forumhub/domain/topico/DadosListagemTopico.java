package br.com.forumhub.domain.topico;

public record DadosListagemTopico(
        String titulo,
        String mensagem,
        String dataCriacao,
        String status,
        String autor,
        String curso
) {

    public DadosListagemTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao().toString(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );
    }
}
