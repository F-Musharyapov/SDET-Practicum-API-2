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
import org.testng.annotations.Test;

import java.io.IOException;

import static helpers.AssertHelper.assertEntityCreateFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Класс тестирования POST-запроса создания сущности
 */
@Epic(value = "Тестирование API")
@Feature(value = "Тестирование POST-запроса создания сущности")
public class CreateEntityTest extends BaseTest {

    /**
     * Экземпляр CreateEntityResponse для хранения ответа после POST запроса создания сущности
     */
    private CreateEntityResponse createEntityResponse;

    @Story(value = "Тестирование позитивного кейса на успешное создание сущности")
    @Test(description = "Тестовый метод для проверки создания сущности")
    public void createEntityTest() {

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

        String entityID = given()
                .spec(requestSpecification)
                .body(createEntityRequest)
                .when()
                .post(config.createEndpoint())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .asString();

        createEntityResponse = CreateEntityResponse.builder()
                .id(entityID.trim())
                .build();

        given()
                .spec(requestSpecification)
                .when()
                .get(config.getEndpoint() + createEntityResponse.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(Integer.parseInt(createEntityResponse.getId())));

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