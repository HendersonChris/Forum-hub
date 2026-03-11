package br.com.forumhub.infra.security;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.jwt}")
    private String chaveSecreta;

    public String gerarToken(Usuario usuarioLogado) {

        String tokenGerado;

        try {
            Algorithm algoritmoAssinatura = Algorithm.HMAC256(chaveSecreta);

            tokenGerado = JWT.create()
                    .withIssuer("API Forum Hub")
                    .withSubject(usuarioLogado.getEmail())
                    .withClaim("id", usuarioLogado.getId())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algoritmoAssinatura);

        } catch (JWTCreationException erroGeracao) {
            throw new ValidacaoException("Erro na criação do token JWT");
        }

        return tokenGerado;
    }

    public String validarToken(String tokenRecebido) {
        try {
            Algorithm algoritmoAssinatura = Algorithm.HMAC256(chaveSecreta);

            return JWT.require(algoritmoAssinatura)
                    .withIssuer("API Forum Hub")
                    .build()
                    .verify(tokenRecebido)
                    .getSubject();

        } catch (JWTVerificationException erroValidacao) {
            throw new ValidacaoException("Token JWT inválido ou expirado.");
        }
    }

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}