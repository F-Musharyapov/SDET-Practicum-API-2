package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateEntityRequest {
    /**
     * Экземпляр класса Addition
     */
    private final Addition addition;

    private final String title;
    private final Boolean verified;

    @JsonProperty("important_numbers")
    private final List<Integer> importantNumbers;

    /**
     * Класс Addition с дополнительными полями
     */
    @Data
    @Builder
    public static class Addition {

        @JsonProperty("additional_info")
        private final String additionalInfo;

        @JsonProperty("additional_number")
        private final int additionalNumber;
    }
}