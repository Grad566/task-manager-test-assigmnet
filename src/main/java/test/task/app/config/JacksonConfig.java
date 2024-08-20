package test.task.app.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Конфигурационный класс для настройки Jackson ObjectMapper.
 * Этот класс настраивает параметры сериализации и добавляет необходимые модули
 * для работы с JSON.
 */
@Configuration
public class JacksonConfig {
    /**
     * Создает и настраивает {@link Jackson2ObjectMapperBuilder} для конфигурации ObjectMapper.
     * @return объект {@link Jackson2ObjectMapperBuilder}, настроенный для сериализации.
     */
    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        var builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(new JsonNullableModule());
        return builder;
    }
}
