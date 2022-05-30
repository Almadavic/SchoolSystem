package application.dto;

import application.entity.Responsibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"name"})
public class ResponsibilityDto {

    @JsonProperty("name")
    private String name;

    public ResponsibilityDto () {

    }

    public ResponsibilityDto(Responsibility responsibility) {
        this.name = responsibility.getName();
    }

}
