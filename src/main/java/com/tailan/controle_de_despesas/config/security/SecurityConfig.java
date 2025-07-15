package com.tailan.controle_de_despesas.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())  // Desabilita proteção CSRF (Cross-Site Request Forgery) para APIs RESTful sem estado
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Configura a política de criação de sessão como STATELESS (sem estado).
                // JWT não usa sessões do servidor.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .anyRequest().authenticated())
                //ADICIONA O FILTRO JWT NA CADEIA DE FILTROS DO SPRING SECURITY
                //O FILTRO JWT VAI SER EXECUTADO ANTES DO USERNAMEPASSWORDAUTHENTICATIONFILTER
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                //desabilita o login de formulario padrão do security, já que estou usando baseada em token
                .formLogin(form -> form.disable())
                // Desabilitaa autenticação HTTP Basic padrão, pois também estou usando autenticação baseada em token.
                .httpBasic(httpBasic -> httpBasic.disable())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        //USADO PARA AUTENTICAR UM USUARIO (NO LOGIN DO CONTROLLER)
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //DEFINE O CODIFICADOR DE SENHAS
        return new BCryptPasswordEncoder();
    }

}
