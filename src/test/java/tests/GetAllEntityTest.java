package tests;

import dto.CreateEntityRequest;
import dto.CreateEntityResponse;
import helpers.BaseRequests;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования GET/ALL-запроса получения данных сущностей
 */
@Epic(value = "Тестирование API")
@Feature(value = "Тестирование GET-запроса получения списка сущностей")
public class GetAllEntityTest extends BaseTest {

    /**
     * Экземпляр CreateEntityResponse для хранения ответа после POST запроса создания первой сущности
     */
    private CreateEntityResponse createEntityResponseFirst;

    /**
     * Экземпляр CreateEntityResponse для хранения ответа после POST запроса создания второй сущности
     */
    private CreateEntityResponse createEntityResponseSecond;

    /**
     * Метод для создания сущностей перед тестом
     */
    @BeforeMethod
    public void createEntity() {

        CreateEntityRequest.Addition addition = CreateEntityRequest.Addition.builder()
                .additionalInfo(getRandomAdditionalInfo())
                .additionalNumber(getRandomAdditionalNumber())
                .build();

        CreateEntityRequest createEntityRequest = CreateEntityRequest.builder()
                .addition(addition)
                .title(getRandomTitle())
                .verified(getRandomVerified())
                .importantNumbers(generateImportantNumbers())
                .build();

        String entityIDFirst = given()
                .spec(requestSpecification)
                .body(createEntityRequest)
                .when()
                .post(config.createEndpoint())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        createEntityResponseFirst = CreateEntityResponse.builder()
                .id(entityIDFirst.trim())
                .build();

        String entityIDSecond = given()
                .spec(requestSpecification)
                .body(createEntityRequest)
                .when()
                .post(config.createEndpoint())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        createEntityResponseSecond = CreateEntityResponse.builder()
                .id(entityIDSecond.trim())
                .build();
    }

    @Story(value = "Тестирование позитивного кейса на успешное получение списка сущностей")
    @Test(description = "Тестовый метод для получения списка сущностей и проверки наличия в списке ранее созданных")
    public void getAllEntityTest() {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get(config.getAllEndpoint())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        List<Integer> allEntities = response.jsonPath().getList("entity.id");
        List<Integer> createdEntities = Arrays.asList(Integer.parseInt(createEntityResponseFirst.getId()), Integer.parseInt(createEntityResponseSecond.getId()));
        Assert.assertTrue(allEntities.containsAll(createdEntities), "Созданные сущности не найдены в ответе");
    }

    /**
     * Метод удаления созданной сущности после завершения тестов
     */
    @AfterMethod
    public void entityAfterCreationDelete() throws IOException {
        BaseRequests.deleteEntityById(createEntityResponseFirst.getId());
        BaseRequests.deleteEntityById(createEntityResponseSecond.getId());
    }
}