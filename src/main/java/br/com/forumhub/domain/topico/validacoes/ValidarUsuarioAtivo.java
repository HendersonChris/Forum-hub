package br.com.forumhub.domain.topico.validacoes;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.topico.DadosCadastroTopico;
import br.com.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarUsuarioAtivo implements ValidaCadastroTopico {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Override
    public void validar(DadosCadastroTopico dadosCadastro) {

        if (!repositorioUsuario.existsUsuarioByIdAndAtivoTrue(dadosCadastro.idUsuario())) {
            throw new ValidacaoException("Este usuário se encontra inativo no sistema.");
        }
    }
}
