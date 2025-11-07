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

import static helpers.AssertHelper.assertEntityPatchFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования PATCH-запроса обновления сущности
 */
@Epic(value = "Тестирование API")
@Feature(value = "Тестирование API пяти точек")
public class PatchEntityTest extends BaseTest {

    /**
     * Экземпляр CreateEntityRequest для хранения ответа POST запроса создания сущности
     */
    private CreateEntityRequest createEntityRequest;

    /**
     * Экземпляр CreateEntityResponse для хранения ответа после POST запроса создания сущности
     */
    private CreateEntityResponse createEntityResponse;

    /**
     * Экземпляр GetEntityResponse для хранения ответа после GET запроса после обновления данных сущности
     */
    private GetEntityResponse getEntityResponseAfterPatch;

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

    @Story(value = "Тестирование PATCH-запроса изменения данных сущности")
    @Test(description = "Тестовый метод для изменения данных объекта")
    public void patchEntityTest() {

        CreateEntityRequest.Addition addition = CreateEntityRequest.Addition.builder()
                .additionalInfo(getRandomAdditionalInfo())
                .additionalNumber(getRandomAdditionalNumber())
                .build();

        CreateEntityRequest requestEntityUpdate = CreateEntityRequest.builder()
                .addition(addition)
                .title(getRandomTitle())
                .verified(getRandomVerified())
                .importantNumbers(generateImportantNumbers())
                .build();

        given()
                .spec(requestSpecification)
                .body(requestEntityUpdate)
                .when()
                .patch(config.patchEndpoint() + createEntityResponse.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();

        getEntityResponseAfterPatch = given()
                .spec(requestSpecification)
                .when()
                .get(config.getEndpoint() + createEntityResponse.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(GetEntityResponse.class);
        assertEntityPatchFieldsEqual(createEntityRequest, getEntityResponseAfterPatch);
    }

    /**
     * Метод удаления созданной сущности после завершения тестов
     */
    @AfterMethod
    public void entityAfterCreationDelete() throws IOException {
        BaseRequests.deleteEntityById(createEntityResponse.getId());
    }
}