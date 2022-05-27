package application.service.serviceLayer.interfacee;


import application.entity.Address;
import application.entity.users.User;
import application.form.RegisterAddressForm;
import application.form.RegisterUserForm;

import java.security.Principal;

public interface ExtendsUserService<T> extends AllUserTypeService { // Apenas as classes que extendes UserDto vaõ implementar essa interface!

    T save(RegisterUserForm userForm);

    default void convertFromFormToUser(User user, RegisterUserForm userForm) {
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
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
