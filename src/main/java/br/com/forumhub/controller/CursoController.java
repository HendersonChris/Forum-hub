package br.com.forumhub.controller;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.curso.*;
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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repositorioCurso;

    @PostMapping
    @Transactional
    public ResponseEntity criarCurso(
            @RequestBody @Valid DadosCadastroCurso dadosCadastro,
            UriComponentsBuilder construtorUri
    ) {

        var novoCurso = new Curso(dadosCadastro);
        repositorioCurso.save(novoCurso);

        var enderecoCurso = construtorUri
                .path("/cursos/{id}")
                .buildAndExpand(novoCurso.getId())
                .toUri();

        return ResponseEntity.created(enderecoCurso)
                .body(new DadosDetalhamentoCurso(novoCurso));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharCurso(@PathVariable Long idCurso) {

        var cursoEncontrado = repositorioCurso.getReferenceById(idCurso);

        return ResponseEntity.ok(new DadosDetalhamentoCurso(cursoEncontrado));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCurso>> listarCurso(
            @PageableDefault(sort = {"nome"}, size = 10, page = 0) Pageable paginacao) {

        var listaCursos = repositorioCurso
                .findAll(paginacao)
                .map(DadosListagemCurso::new);

        return ResponseEntity.ok(listaCursos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterarCurso(@RequestBody @Valid DadosAlterarCurso dadosAlteracao) {

        Long idCurso = dadosAlteracao.id();

        var referenciaCurso = repositorioCurso.findById(idCurso);

        if (referenciaCurso.isEmpty()) {
            throw new ValidacaoException("Curso inexistente no banco de dados");
        }

        var cursoEncontrado = referenciaCurso.get();
        cursoEncontrado.atualizar(dadosAlteracao);

        return ResponseEntity.ok(new DadosDetalhamentoCurso(cursoEncontrado));
    }
}