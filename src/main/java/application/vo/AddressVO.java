package application.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AddressVO { //  Representa os endereços dos usuários

    private String city;
    private String state;
    private String country;

    public AddressVO(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

}
