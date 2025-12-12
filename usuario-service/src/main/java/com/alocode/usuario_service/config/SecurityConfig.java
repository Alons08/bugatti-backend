package com.alocode.usuario_service.config;


import com.alocode.usuario_service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//CLASE DE LA CADENA DE FILTROS
@Configuration
@EnableWebSecurity  // Habilita la seguridad web de Spring
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro personalizado para JWT
    private final AuthenticationProvider authProvider; // Proveedor de autenticación (configurado en ApplicationConfig)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf
                                .disable()) // Desactiva CSRF (común en APIs REST stateless)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                            .requestMatchers("/auth/login", "/auth/register", "/auth/forgot-password", "/auth/reset-password").permitAll() // públicas
                            .requestMatchers("/auth/me").authenticated() // privada
                            .requestMatchers("/admin/user/email/*").authenticated() // privada, pero permite cualquier usuario autenticado para llamadas internas
                            .requestMatchers("/admin/user/*").hasAnyAuthority("ADMIN", "ALMACENERO") // privada
                            .requestMatchers("/admin/**").hasAuthority("ADMIN") // privada 
                            .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin estado (no usa sesiones)
                .authenticationProvider(authProvider) // Registra el proveedor de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añade nuestro filtro JWT antes del filtro de login tradicional
                .build();
    }
}