package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import config.BaseConfig;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.given;

/**
 * Базовый тестовый класс с общими настройками для запросов REST API
 */
public class BaseRequests {

    /**
     * Экземпляр конфигурации с параметрами запроса
     */
    private static BaseConfig config;

    /**
     * Конструктор BaseConfig с инициализацией настройки RestAssured
     *
     * @param config с параметрами
     */
    public BaseRequests(BaseConfig config) {
        this.config = config;
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.registerModule(new JavaTimeModule());
                            return mapper;
                        }));
    }

    /**
     * Метод для получения спецификации RestAssured с базовыми настройками
     *
     * @return объект RequestSpecification с настройками
     * @throws IOException если ошибки при формировании спецификации
     */
    @Description("Создание базовой спецификации REST-запроса с настройками из конфигурации")
    public static RequestSpecification initRequestSpecification() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setBaseUri(config.baseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        return requestSpecBuilder.build();
    }

    /**
     * Удаление сущности с заданным ID
     *
     * @param entityID ID сущности, которую необходимо удалить
     */
    @Description("Дефолтный метод для удаления сущностей после тестов")
    public static void deleteEntityById(String entityID) throws IOException {
        given()
                .spec(initRequestSpecification())
                .when()
                .delete(config.deleteEndpoint() + entityID)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}