package application.form;


import javax.validation.constraints.NotBlank;

public class RegisterAddressForm { // Form que recebe  um novo endereço do estudante que será cadastrado!

    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;

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
