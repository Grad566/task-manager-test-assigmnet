package test.task.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import test.task.app.service.CustomUserDetailsService;

/**
 * Конфигурационный класс для настройки безопасности приложения.
 * Этот класс настраивает аутентификацию, авторизацию и управление сессиями
 * с использованием Spring Security.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtDecoder jwtDecoder;

    /**
     * Создает и настраивает {@link AuthenticationManager} для управления аутентификацией.
     * @param http объект {@link HttpSecurity}, используемый для настройки безопасности.
     * @return объект {@link AuthenticationManager}, настроенный для аутентификации.
     * @throws Exception если возникает ошибка при создании менеджера аутентификации.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    /**
     * Создает и настраивает {@link AuthenticationProvider} для аутентификации пользователей
     * с использованием DAO.
     * @param auth объект {@link AuthenticationManagerBuilder}, используемый для настройки провайдера аутентификации.
     * @return объект {@link AuthenticationProvider}, настроенный для аутентификации пользователей.
     */
    @Bean
    public AuthenticationProvider daoAuthProvider(AuthenticationManagerBuilder auth) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Создает и настраивает {@link SecurityFilterChain} для обработки запросов и настройки безопасности.
     * @param http объект {@link HttpSecurity}, используемый для настройки безопасности.
     * @param introspector объект {@link HandlerMappingIntrospector}, используемый для создания MVC матчеров.
     * @return объект {@link SecurityFilterChain}, настроенный для обработки запросов
     * @throws Exception если возникает ошибка при настройке цепочки фильтров безопасности.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/index.html")).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((rs) -> rs.jwt((jwt) -> jwt.decoder(jwtDecoder)))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
