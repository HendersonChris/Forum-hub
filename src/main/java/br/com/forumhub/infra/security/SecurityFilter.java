package br.com.forumhub.infra.security;

import br.com.forumhub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService servicoToken;

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao,
                                    HttpServletResponse resposta,
                                    FilterChain cadeiaFiltros) throws ServletException, IOException {

        var tokenRecuperado = recuperarTokenCabecalho(requisicao);

        if (tokenRecuperado != null) {
            var loginUsuario = servicoToken.validarToken(tokenRecuperado);
            var usuarioAutenticado = repositorioUsuario.findByEmailAndAtivoTrue(loginUsuario);

            var autenticacao = new UsernamePasswordAuthenticationToken(
                    usuarioAutenticado,
                    null,
                    usuarioAutenticado.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }

        cadeiaFiltros.doFilter(requisicao, resposta);
    }

    private String recuperarTokenCabecalho(HttpServletRequest requisicaoHttp) {

        var cabecalhoAutorizacao = requisicaoHttp.getHeader("Authorization");

        if (cabecalhoAutorizacao != null) {
            return cabecalhoAutorizacao.replace("Bearer ", "");
        }

        return null;
    }
}
