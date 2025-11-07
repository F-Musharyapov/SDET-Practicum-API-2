package helpers;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс с константами
 */
public class TestDataHelper {

    /**
     * Экземпляр для Faker
     */
    private static final Faker faker = new Faker();

    /**
     * Экземпляр для Random
     */
    private static final Random random = new Random();

    /**
     * Идентификатор title
     */
    public static final String TITLE = "[a-z]{8} [a-z]{6} [a-z]{4}";

    /**
     * Идентификатор verified
     */
    public static final Boolean[] VERIFIED = {true, false};

    /**
     * Метод генерации рандомной информации сущности
     *
     * @return сгенерированные данные
     */
    public static String getRandomAdditionalInfo() {
        return faker.lorem().sentence(2, 4);
    }

    /**
     * Метод генерации рандомного номера сущности
     *
     * @return сгенерированные данные
     */
    public static int getRandomAdditionalNumber() {
        return ThreadLocalRandom.current().nextInt(100, 1000);
    }

    /**
     * Метод генерации тайтла
     *
     * @return сгенерированные данные
     */
    public static String getRandomTitle() {
        return faker.regexify(TITLE);
    }

    /**
     * Метод генерации статуса сущности
     *
     * @return сгенерированные данные
     */
    public static Boolean getRandomVerified() {
        return VERIFIED[random.nextInt(VERIFIED.length)];
    }

    /**
     * Метод генерации рандомных чисел для сущности
     *
     * @return сгенерированные данные
     */
    public static List<Integer> generateImportantNumbers() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return List.of(
                random.nextInt(10, 100),
                random.nextInt(10, 100),
                random.nextInt(10, 100)
        );
    }
}