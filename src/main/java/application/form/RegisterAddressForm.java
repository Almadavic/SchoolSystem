package application.form;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RegisterAddressForm { // Form que recebe  um novo endereço do estudante que será cadastrado!

    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;

}
