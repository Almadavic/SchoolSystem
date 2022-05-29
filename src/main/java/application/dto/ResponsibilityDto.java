package application.dto;

import application.entity.Responsibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"name"})
public class ResponsibilityDto {

    @JsonProperty("name")
    private String name;

    public ResponsibilityDto () {

    }

    public ResponsibilityDto(Responsibility responsibility) {
        this.name = responsibility.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
