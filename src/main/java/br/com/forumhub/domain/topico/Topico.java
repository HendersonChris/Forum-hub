package br.com.forumhub.domain.topico;

import br.com.forumhub.domain.curso.Curso;
import br.com.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    private Curso curso;

    public void atualizar(DadosAtualizacaoTopico dadosAtualizacao) {

        if (dadosAtualizacao.titulo() != null) {
            this.titulo = dadosAtualizacao.titulo();
        }

        if (dadosAtualizacao.mensagem() != null) {
            this.mensagem = dadosAtualizacao.mensagem();
        }

        if (dadosAtualizacao.status() != null) {
            this.status = dadosAtualizacao.status();
        }
    }
}