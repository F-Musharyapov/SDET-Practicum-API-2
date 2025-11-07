package config;

import org.aeonbits.owner.Config;

/**
 * Интерфейс с основной конфигурацией проекта
 */
@Config.Sources({"classpath:config.properties"})
public interface BaseConfig extends Config {

    /**
     * Метод для возвращения значения параметра baseUrl из config.properties
     *
     * @return базовый URL
     */
    String baseUrl();

    /**
     * Эндпоинт создания сущности
     *
     * @return эндпоинт
     */
    String createEndpoint();

    /**
     * Эндпоинт чтения сущности
     *
     * @return эндпоинт
     */
    String getEndpoint();

    /**
     * Эндпоинт чтения списка сущностей
     *
     * @return эндпоинт
     */
    String getAllEndpoint();

    /**
     * Эндпоинт обновления/дополнения сущности
     *
     * @return эндпоинт
     */
    String patchEndpoint();

    /**
     * Эндпоинт удаления сущности
     *
     * @return эндпоинт
     */
    String deleteEndpoint();
}