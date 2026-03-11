package br.com.forumhub.controller;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarUsuario(
            @RequestBody @Valid DadosCadastroUsuario dadosCadastro,
            UriComponentsBuilder construtorUri) {

        if (repositorioUsuario.existsByEmail(dadosCadastro.email())) {
            throw new ValidacaoException("Email já cadastrado na aplicação.");
        }

        var novoUsuario = new Usuario(dadosCadastro);

        repositorioUsuario.save(novoUsuario);

        var enderecoNovoUsuario = construtorUri
                .path("/usuarios/{id}")
                .buildAndExpand(novoUsuario.getId())
                .toUri();

        return ResponseEntity.created(enderecoNovoUsuario)
                .body(new DadosDetalhamentoUsuario(novoUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharUsuario(@PathVariable Long idUsuario) {

        var usuarioEncontrado = repositorioUsuario.getReferenceById(idUsuario);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuarioEncontrado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarUsuario(@PathVariable Long idUsuario) {

        var usuarioEncontrado = repositorioUsuario.getReferenceById(idUsuario);
        usuarioEncontrado.deletar();

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterarUsuario(@RequestBody @Valid DadosAlterarUsuario dadosAlteracao) {

        Long idUsuario = dadosAlteracao.id();

        if (!repositorioUsuario.existsUsuarioByIdAndAtivoTrue(idUsuario)) {
            throw new ValidacaoException("O usuário não existe no sistema.");
        }

        var usuarioEncontrado = repositorioUsuario.getReferenceById(idUsuario);
        usuarioEncontrado.atualizar(dadosAlteracao);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuarioEncontrado));
    }
}