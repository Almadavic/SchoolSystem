package application.service.serviceLayer.interfacee;


import application.dto.StudentDto;
import application.dto.UserDto;
import application.entity.Address;
import application.entity.users.Student;
import application.entity.users.User;
import application.form.RegisterAddressForm;
import application.form.RegisterUserForm;
import application.repository.ExtendsUserRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.service.exception.general.InvalidParam;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ExtendsUserService extends AllUserTypeService { // Apenas as classes que extendes UserDto vaõ implementar essa interface!

    UserDto save(RegisterUserForm userForm);

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
