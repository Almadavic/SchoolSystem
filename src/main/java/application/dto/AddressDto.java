package application.dto;

import application.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"city","state","country"}) // Ordenação de atributos
public class AddressDto {  // DTO da classe Address
    @JsonProperty(value="city") // nome do atributo, para ordenar na linha 9
    private String city;
    @JsonProperty(value="state")
    private String state;
    @JsonProperty(value="country")
    private String country;

    public AddressDto(Address address) {  // Transformando um Address em AddressDto
        this.city=address.getCity();
        this.state=address.getState();
        this.country=address.getCountry();
    }

}
