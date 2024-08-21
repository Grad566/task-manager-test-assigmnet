package test.task.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Главный класс приложения.
 * Этот класс является точкой входа в приложение Spring Boot. Он включает
 * аннотацию {@link SpringBootApplication}, которая включает в себя
 * автоматическую конфигурацию, сканирование компонентов и настройку
 * Spring. Также включает аннотацию {@link EnableJpaAuditing} для
 * активации аудита JPA.
 */
@SpringBootApplication
@EnableJpaAuditing
public class AppApplication {
    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
