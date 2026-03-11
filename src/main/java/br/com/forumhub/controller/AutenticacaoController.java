package br.com.forumhub.controller;

import br.com.forumhub.domain.usuario.DadosLoginUsuario;
import br.com.forumhub.domain.usuario.Usuario;
import br.com.forumhub.domain.usuario.UsuarioRepository;
import br.com.forumhub.infra.security.DadosToken;
import br.com.forumhub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Autowired
    private AuthenticationManager gerenciadorAutenticacao;

    @Autowired
    private TokenService servicoToken;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLoginUsuario credenciaisLogin) {

        System.out.println("LOG: REQUISIÇÃO DE LOGIN RECEBIDA");

        var tokenCredenciais = new UsernamePasswordAuthenticationToken(
                credenciaisLogin.login(),
                credenciaisLogin.senha()
        );

        var resultadoAutenticacao = gerenciadorAutenticacao.authenticate(tokenCredenciais);
        var tokenGerado = servicoToken.gerarToken((Usuario) resultadoAutenticacao.getPrincipal());

        return ResponseEntity.ok(new DadosToken(tokenGerado));
    }
}