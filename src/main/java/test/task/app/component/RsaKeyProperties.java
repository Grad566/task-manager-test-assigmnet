package test.task.app.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Класс для хранения свойств ключей RSA.
 * Этот класс используется для загрузки и хранения открытого и закрытого ключей RSA
 * из конфигурации приложения.
 */
@Component
@ConfigurationProperties(prefix = "rsa")
@Getter
@Setter
public class RsaKeyProperties {
    /**
     * Открытый ключ RSA.
     */
    private RSAPublicKey publicKey;
    /**
     * Закрытый ключ RSA.
     */
    private RSAPrivateKey privateKey;
}
