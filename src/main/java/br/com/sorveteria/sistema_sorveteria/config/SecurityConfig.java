package br.com.sorveteria.sistema_sorveteria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // 🔓 FRONT (NÃO QUEBRA)
                        .requestMatchers(HttpMethod.GET,
                                "/atendentes",
                                "/sabores",
                                "/tamanhos",
                                "/pedidos/**",
                                "/relatorios/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/pedidos"
                        ).permitAll()

                        // 🔒 ADMIN
                        .requestMatchers(HttpMethod.POST,
                                "/atendentes"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/atendentes/*/inativar",
                                "/pedidos/*/inativar"
                        ).hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()); //  BASIC AUTH

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            br.com.sorveteria.sistema_sorveteria.auth.UserDetailsServiceImpl uds
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
