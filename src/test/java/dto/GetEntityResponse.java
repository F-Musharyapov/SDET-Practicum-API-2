package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetEntityResponse {

    private int id;
    private String title;
    private Boolean verified;

    /**
     * Экземпляр класса Addition
     */
    private Addition addition;

    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;


    /**
     * Класс Addition с дополнительными полями
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Addition {
        @JsonProperty("additional_info")
        private String additionalInfo;
        @JsonProperty("additional_number")
        private int additionalNumber;
    }
}