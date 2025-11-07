package tests;

import dto.CreateEntityRequest;
import dto.CreateEntityResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования DELETE-запроса удаления сущности
 */
@Epic(value = "Тестирование API")
@Feature(value = "Тестирование DELETE-запроса удаления сущности")
public class DeleteEntityTest extends BaseTest {

    /**
     * Экземпляр CreateEntityResponse для хранения ответа после POST запроса создания сущности
     */
    private CreateEntityResponse createEntityResponse;

    /**
     * Метод для создания сущности перед тестом
     */
    @BeforeMethod
    public void createEntity() {

        CreateEntityRequest.Addition addition = CreateEntityRequest.Addition.builder()
                .additionalInfo(getRandomAdditionalInfo())
                .additionalNumber(getRandomAdditionalNumber())
                .build();

        CreateEntityRequest createRequest = CreateEntityRequest.builder()
                .addition(addition)
                .title(getRandomTitle())
                .verified(getRandomVerified())
                .importantNumbers(generateImportantNumbers())
                .build();

        String entityID = given()
                .spec(requestSpecification)
                .body(createRequest)
                .when()
                .post(config.createEndpoint())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        createEntityResponse = CreateEntityResponse.builder()
                .id(entityID.trim())
                .build();
    }

    @Story(value = "Тестирование позитивного кейса на успешное удаление сущности")
    @Test(description = "Тестовый метод для проверки удаления сущности")
    public void deleteEntityTest() {
        given()
                .spec(requestSpecification)
                .when()
                .delete(config.deleteEndpoint() + createEntityResponse.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        given()
                .spec(requestSpecification)
                .when()
                .get(config.getEndpoint() + createEntityResponse.getId())
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}