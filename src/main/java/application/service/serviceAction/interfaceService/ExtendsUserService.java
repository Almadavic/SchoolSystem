package application.service.serviceAction.interfaceService;


import application.dto.response.UserDto;
import application.domain.vo.AddressVO;
import application.domain.entity.user.User;
import application.dto.request.RegisterAddressForm;
import application.dto.request.RegisterUserForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public interface ExtendsUserService extends AllUserTypeService { // Interface onde StudentRepository e TeacherRepository vão implementar

    UserDto save(RegisterUserForm userForm);

    default void convertFromFormToUser(User user, RegisterUserForm userForm) {
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        AddressVO address = convertFromFormToAddress(userForm.getAddressForm());
        user.setAddress(address);
    }

    default AddressVO convertFromFormToAddress(RegisterAddressForm addressForm) {
        AddressVO address = new AddressVO();
        address.setCity(addressForm.getCity());
        address.setState(addressForm.getState());
        address.setCountry(addressForm.getCountry());
        return address;
    }

}
