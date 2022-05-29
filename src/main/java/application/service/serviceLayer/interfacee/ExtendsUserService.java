package application.service.serviceLayer.interfacee;



import application.dto.UserDto;
import application.entity.Address;
import application.entity.users.User;
import application.form.RegisterAddressForm;
import application.form.RegisterUserForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public interface ExtendsUserService extends AllUserTypeService { // Apenas as classes que extendes UserDto vaõ implementar essa interface!

    UserDto save(RegisterUserForm userForm);

    default void convertFromFormToUser(User user, RegisterUserForm userForm) {
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        Address address = convertFromFormToAddress(user, userForm.getAddressForm());
        user.setAddress(address);
    }

    default Address convertFromFormToAddress(User user, RegisterAddressForm addressForm) {
        Address address = new Address();
        address.setUser(user);
        address.setCity(addressForm.getCity());
        address.setState(addressForm.getState());
        address.setCountry(addressForm.getCountry());
        return address;
    }

}
