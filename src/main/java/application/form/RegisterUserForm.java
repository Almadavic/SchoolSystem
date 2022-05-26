package application.form;


import application.service.exception.general.InvalidParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterUserForm { // Form que recebe informações do estudante que será cadastrado!

   @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private RegisterAddressForm addressForm; // Resolver a anotação emcima!


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterAddressForm getAddressForm() {
        return addressForm;
    }

    public void setAddressForm(RegisterAddressForm addressForm) {
        this.addressForm = addressForm;
    }
}
