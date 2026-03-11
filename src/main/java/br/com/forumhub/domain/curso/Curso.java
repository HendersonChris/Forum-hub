package br.com.forumhub.domain.curso;

import br.com.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private List<Topico> listaTopicosCurso;

    public Curso(DadosCadastroCurso dadosCadastro) {
        this.nome = dadosCadastro.nome();
        this.categoria = dadosCadastro.categoria();
    }

    public void atualizar(DadosAlterarCurso dadosAlteracao) {
        if (dadosAlteracao.nome() != null) {
            this.nome = dadosAlteracao.nome();
        }
    }
}