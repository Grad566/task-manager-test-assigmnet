package test.task.app.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Утилита для работы с JSON Web Token (JWT).
 * Этот компонент предоставляет методы для генерации JWT на основе
 * имени пользователя. Использует {@link JwtEncoder} для кодирования токенов.
 */
@Component
@RequiredArgsConstructor
public class JWTUtils {
    private final JwtEncoder jwtEncoder;

    /**
     * Генерирует JWT для указанного пользователя.
     * @param username имя пользователя, для которого генерируется токен
     * @return сгенерированный JWT в виде строки
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
