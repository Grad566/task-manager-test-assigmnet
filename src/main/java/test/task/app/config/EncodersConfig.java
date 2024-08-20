package test.task.app.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import test.task.app.component.RsaKeyProperties;


/**
 * Конфигурационный класс для настройки кодировщиков и декодировщиков JWT,
 * а также для настройки кодировщика паролей.
 * Этот класс использует свойства ключей RSA для создания JWT-кодировщика и декодировщика.
 */
@Configuration
@RequiredArgsConstructor
public class EncodersConfig {
    private final RsaKeyProperties rsaKeys;

    /**
     * Создает и настраивает {@link BCryptPasswordEncoder} для кодирования паролей.
     * @return объект {@link BCryptPasswordEncoder}, используемый для хеширования паролей.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает и настраивает {@link JwtEncoder} для кодирования JWT.
     * @return объект {@link JwtEncoder}, используемый для создания JWT.
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey()).privateKey(rsaKeys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Создает и настраивает {@link JwtDecoder} для декодирования JWT.
     * @return объект {@link JwtDecoder}, используемый для проверки и декодирования JWT.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }
}
