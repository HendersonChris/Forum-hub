package br.com.forumhub.domain.usuario;

import br.com.forumhub.domain.topico.Topico;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Topico> listaTopicosUsuario = new ArrayList<>();

    private boolean ativo;

    private static final BCryptPasswordEncoder criptografadorSenha = new BCryptPasswordEncoder();

    public Usuario(DadosCadastroUsuario dadosCadastro) {
        this.nome = dadosCadastro.nome();
        this.email = dadosCadastro.email();
        this.senha = gerarHashSenha(dadosCadastro.senha());
        this.ativo = true;
    }

    private String gerarHashSenha(String senhaUsuario) {
        return criptografadorSenha.encode(senhaUsuario);
    }

    public void deletar() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void atualizar(DadosAlterarUsuario dadosAlteracao) {

        if (dadosAlteracao.nome() != null) {
            this.nome = dadosAlteracao.nome();
        }

        if (dadosAlteracao.senha() != null) {
            this.senha = gerarHashSenha(dadosAlteracao.senha());
        }
    }
}
