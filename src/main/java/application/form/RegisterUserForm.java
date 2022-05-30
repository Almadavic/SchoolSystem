package application.form;


import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
@Getter
public class RegisterUserForm { // Form que recebe informações do estudante que será cadastrado!

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @Valid // Temos que usar isso quando temos um form dentro de outro form!
    private RegisterAddressForm addressForm; // Resolver a anotação emcima!

}
