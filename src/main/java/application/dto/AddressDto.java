package application.dto;

import application.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"city","state","country"}) // Ordenação de atributos
public class AddressDto {  // DTO da classe Address
    @JsonProperty(value="city") // nome do atributo, para ordenar na linha 9
    private String city;
    @JsonProperty(value="state")
    private String state;
    @JsonProperty(value="country")
    private String country;

    public AddressDto() {

    }

    public AddressDto(Address address) {  // Transformando um Address em AddressDto
        this.city=address.getCity();
        this.state=address.getState();
        this.country=address.getCountry();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
