package br.com.forumhub.domain.topico.validacoes;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.topico.DadosCadastroTopico;
import br.com.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoDuplicado implements ValidaCadastroTopico {

    @Autowired
    private TopicoRepository repositorioTopico;

    @Override
    public void validar(DadosCadastroTopico dadosCadastro) {

        if (repositorioTopico.existsByMensagemAndTitulo(
                dadosCadastro.mensagem(),
                dadosCadastro.titulo()
        )) {
            throw new ValidacaoException("Este tópico já se encontra cadastrado.");
        }
    }
}
