package application.form;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class RegisterUserForm { // Form que recebe informações do estudante que será cadastrado!

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @Valid // Temos que usar isso quando temos um form dentro de outro form!
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
