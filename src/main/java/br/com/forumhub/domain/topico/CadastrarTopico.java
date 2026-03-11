package br.com.forumhub.domain.topico;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.curso.CursoRepository;
import br.com.forumhub.domain.topico.validacoes.ValidaCadastroTopico;
import br.com.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CadastrarTopico {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Autowired
    private CursoRepository repositorioCurso;

    @Autowired
    private TopicoRepository repositorioTopico;

    @Autowired
    private List<ValidaCadastroTopico> listaValidacoes;

    public DadosDetalhamentoTopico cadastrar(DadosCadastroTopico dadosCadastro) {

        if (!repositorioUsuario.existsById(dadosCadastro.idUsuario())) {
            throw new ValidacaoException("Usuário com ID não encontrado.");
        }

        if (!repositorioCurso.existsById(dadosCadastro.idCurso())) {
            throw new ValidacaoException("Curso com ID não encontrado");
        }

        listaValidacoes.forEach(validacaoAtual -> validacaoAtual.validar(dadosCadastro));

        var autorTopico = repositorioUsuario.getReferenceById(dadosCadastro.idUsuario());
        var cursoTopico = repositorioCurso.getReferenceById(dadosCadastro.idCurso());
        LocalDateTime momentoCriacao = LocalDateTime.now();

        var novoTopico = new Topico(
                null,
                dadosCadastro.titulo(),
                dadosCadastro.mensagem(),
                momentoCriacao,
                "NÃO RESPONDIDO",
                autorTopico,
                cursoTopico
        );

        repositorioTopico.save(novoTopico);

        return new DadosDetalhamentoTopico(novoTopico);
    }
}
