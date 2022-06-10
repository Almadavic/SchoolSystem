package application.services.serviceLayer.interfaces;


import application.dtos.UserDto;
import application.entities.Address;
import application.entities.users.User;
import application.forms.RegisterAddressForm;
import application.forms.RegisterUserForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public interface ExtendsUserService extends AllUserTypeService { // Interface onde StudentRepository e TeacherRepository vão implementar

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
