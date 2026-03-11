package br.com.forumhub.controller;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private CadastrarTopico servicoCadastroTopico;

    @Autowired
    private TopicoRepository repositorioTopico;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTopico(
            @RequestBody @Valid DadosCadastroTopico dadosCadastro,
            UriComponentsBuilder construtorUri) {

        var topicoCadastrado = servicoCadastroTopico.cadastrar(dadosCadastro);

        var enderecoTopico = construtorUri.path("/topicos/{id}")
                .buildAndExpand(topicoCadastrado.id())
                .toUri();

        return ResponseEntity.created(enderecoTopico).body(topicoCadastrado);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = {"titulo"}, page = 0) Pageable paginacao) {

        var listaPaginada = repositorioTopico.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(listaPaginada);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long idTopico) {

        if (!repositorioTopico.existsById(idTopico)) {
            throw new ValidacaoException("Id de tópico inexistente.");
        }

        var topicoEncontrado = repositorioTopico.getReferenceById(idTopico);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topicoEncontrado));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(
            @PathVariable Long idTopico,
            @RequestBody DadosAtualizacaoTopico dadosAtualizacao) {

        var topicoEncontrado = repositorioTopico.findById(idTopico)
                .orElseThrow(() ->
                        new ValidacaoException("Tópico inexistente no banco de dados.")
                );

        topicoEncontrado.atualizar(dadosAtualizacao);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topicoEncontrado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarTopico(@PathVariable Long idTopico) {

        if (!repositorioTopico.existsById(idTopico)) {
            throw new ValidacaoException("Nenhum tópico existente com este id no banco de dados");
        }

        repositorioTopico.deleteById(idTopico);

        return ResponseEntity.noContent().build();
    }
}