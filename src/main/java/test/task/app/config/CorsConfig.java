package test.task.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурационный класс для настройки CORS (Cross-Origin Resource Sharing).
 * Этот класс определяет правила CORS для приложения, позволяя
 * управлять доступом к ресурсам из других источников.
 */
@Configuration
public class CorsConfig {

    /**
     * Создает и настраивает {@link WebMvcConfigurer} для конфигурации CORS.
     * @return объект {@link WebMvcConfigurer}, который содержит настройки CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
