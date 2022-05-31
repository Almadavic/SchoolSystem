package application.dto;

import application.entity.Responsibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"name"})
public class ResponsibilityDto {

    @JsonProperty("name")
    private String name;

    public ResponsibilityDto(Responsibility responsibility) {
        this.name = responsibility.getName();
    }

}
