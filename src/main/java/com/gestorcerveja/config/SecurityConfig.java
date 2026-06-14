package com.gestorcerveja.config;

import com.gestorcerveja.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsSource()))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Loja pública
                .requestMatchers(HttpMethod.GET,  "/api/receitas").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/receitas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/pedidos").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/pedidos").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/clientes/register").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/clientes/me").permitAll()
                // Admin requer autenticação
                .requestMatchers("/api/admin/**").authenticated()
                // Resto permite
                .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());
        return http.build();
    }

    /** Autentica contra a tabela Usuario (senha em texto claro — {noop}). */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var u = usuarioRepository.findByNome(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado: " + username));
            return User.withUsername(u.getNome())
                    .password("{noop}" + u.getSenha())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public CorsConfigurationSource corsSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("*"));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
