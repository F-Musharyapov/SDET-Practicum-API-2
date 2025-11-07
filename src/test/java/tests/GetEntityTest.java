package tests;

import dto.CreateEntityRequest;
import dto.CreateEntityResponse;
import dto.GetEntityResponse;
import helpers.BaseRequests;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static helpers.AssertHelper.assertEntityCreateFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования GET-запроса
 */
@Epic(value = "Тестирование API")
@Feature(value = "Тестирование GET-запроса получения сущности")
public class GetEntityTest extends BaseTest {

    /**
     * Экземпляр GetEntityResponse для хранения ответа GET запроса
     */
    private CreateEntityRequest createEntityRequest;

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

        createEntityRequest = CreateEntityRequest.builder()
                .addition(addition)
                .title(getRandomTitle())
                .verified(getRandomVerified())
                .importantNumbers(generateImportantNumbers())
                .build();

        String entityID = given()
                .spec(requestSpecification)
                .body(createEntityRequest)
                .when()
                .post(config.createEndpoint())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();

        createEntityResponse = CreateEntityResponse.builder()
                .id(entityID.trim())
                .build();
    }

    @Story(value = "Тестирование позитивного кейса на успешное получение сущности")
    @Test(description = "Тестовый метод для проверки получаемых данных")
    public void getEntityTest() {

        GetEntityResponse getEntityResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.getEndpoint() + createEntityResponse.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(GetEntityResponse.class);
        assertEntityCreateFieldsEqual(createEntityRequest, getEntityResponse);
    }

    /**
     * Метод удаления созданной сущности после завершения тестов
     */
    @AfterMethod
    public void entityAfterCreationDelete() throws IOException {
        BaseRequests.deleteEntityById(createEntityResponse.getId());
    }
}