package helpers;

import dto.CreateEntityRequest;
import dto.GetEntityResponse;

import java.util.Objects;

/**
 * Набор Assert
 */
public class AssertHelper {

    /**
     * Сравнение данных для класса CreateEntityTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertEntityCreateFieldsEqual(CreateEntityRequest expected, GetEntityResponse actual) {
        assertFieldEquals("Title", expected.getTitle(), actual.getTitle());
        assertFieldEquals("Verified", expected.getVerified(), actual.getVerified());
        assertFieldEquals("Important_numbers", expected.getImportantNumbers(), actual.getImportantNumbers());
        assertFieldEquals("Additional_info", expected.getAddition().getAdditionalInfo(), actual.getAddition().getAdditionalInfo());
        assertFieldEquals("Additional_number", expected.getAddition().getAdditionalNumber(), actual.getAddition().getAdditionalNumber());
    }

    /**
     * Сравнение данных для класса PatchEntityTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertEntityPatchFieldsEqual(CreateEntityRequest expected, GetEntityResponse actual) {
        assertFieldNotEquals("Title", expected.getTitle(), actual.getTitle());
        assertFieldNotEquals("Important_numbers", expected.getImportantNumbers(), actual.getImportantNumbers());
        assertFieldNotEquals("Additional_info", expected.getAddition().getAdditionalInfo(), actual.getAddition().getAdditionalInfo());
        assertFieldNotEquals("Additional_number", expected.getAddition().getAdditionalNumber(), actual.getAddition().getAdditionalNumber());
    }

    /**
     * Метод проверки совпадения проверяемого поля
     *
     * @param fieldName     проверяемое поле
     * @param expectedValue ожидаемые данные
     * @param actualValue   актуальные данные
     */
    private static void assertFieldEquals(String fieldName, Object expectedValue, Object actualValue) {
        if (!Objects.equals(expectedValue, actualValue)) {
            throw new AssertionError(fieldName + " не совпадает: expected= " + expectedValue + ", actual= " + actualValue);
        }
    }

    /**
     * Метод проверки несовпадения проверяемого поля для класса PatchEntityTest
     *
     * @param fieldName     проверяемое поле
     * @param expectedValue ожидаемые данные
     * @param actualValue   актуальные данные
     */
    private static void assertFieldNotEquals(String fieldName, Object expectedValue, Object actualValue) {
        if (Objects.equals(expectedValue, actualValue)) {
            throw new AssertionError(fieldName + " не должно совпадать: expected= " + expectedValue + ", actual= " + actualValue);
        }
    }
}