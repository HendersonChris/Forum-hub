package br.com.forumhub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter filtroSeguranca;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity configuracaoHttp) throws Exception {
        return configuracaoHttp
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configuracaoSessao ->
                        configuracaoSessao.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requisicoes -> {
                    requisicoes.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    requisicoes.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    requisicoes.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**"
                    ).permitAll();
                    requisicoes.anyRequest().authenticated();
                })
                .addFilterBefore(filtroSeguranca, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuracaoAutenticacao)
            throws Exception {
        return configuracaoAutenticacao.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encriptadorSenha() {
        return new BCryptPasswordEncoder();
    }
}
