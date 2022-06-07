package application.repositories;

import application.entities.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByEmailSuccess() {

        String userEmail = "almadavic@live.com";
        User user = userRepository.findByEmail(userEmail).get();
        Assertions.assertEquals(userEmail,user.getEmail());
    }

    @Test
    public void findUserByEmailFail() {

        String userEmail = "alojaoj@hotmail.com";
        Assertions.assertThrows(NoSuchElementException.class,()-> userRepository.findByEmail(userEmail).get());
    }

    @Test
    public void removeUser() {

        String email = "almadavic@live.com";
        User user = userRepository.findByEmail(email).get();
        Assertions.assertDoesNotThrow(()->NoSuchElementException.class);
        userRepository.delete(user);
        Assertions.assertThrows(NoSuchElementException.class, () -> userRepository.findByEmail(email).get());
    }

    @Test
    public void findUserByParameterSuccess() { // Verificar se a busca dos usuários pelas roles está correto!

        String roleNameTeacher = "ROLE_TEACHER";
        List<User> teachers = userRepository.findByRolesName(roleNameTeacher);
        boolean teacherRoleValidation = validationRole(roleNameTeacher,teachers);
        Assertions.assertFalse(teacherRoleValidation);
        Assertions.assertEquals(2,teachers.size());

        String roleNamePrincipal = "ROLE_PRINCIPAL";
        List<User> principals = userRepository.findByRolesName(roleNamePrincipal);
        boolean principalRoleValidation = validationRole(roleNamePrincipal,principals);
        Assertions.assertFalse(principalRoleValidation);
        Assertions.assertEquals(1,principals.size());


        String roleNameStudent = "ROLE_STUDENT";
        List<User> students = userRepository.findByRolesName(roleNameStudent);
        boolean studentRoleValidation = validationRole(roleNameStudent,students);
        Assertions.assertFalse(studentRoleValidation);
        Assertions.assertEquals(4,students.size());

    }
    private boolean validationRole(String roleName, List<User> users) {
       boolean thereIsAnotherRole = false;
        if(users.stream().anyMatch(user -> user.getRoles().get(0).getName()!=roleName)) {
            thereIsAnotherRole = true;
        }
        return thereIsAnotherRole;
    }
}