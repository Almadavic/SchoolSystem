package application.integrationTest.repository;

import application.domain.entity.user.User;
import application.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Profile(value = "test")
public class UserRepositoryTest { // TESTE DE INTEGRAÇÃO

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmailSuccess() { // Encontra um user pelo email. SUCESSO!

        String userEmail = "almadavic@live.com";
        User user = userRepository.findByEmail(userEmail).get();
        Assertions.assertEquals(userEmail, user.getEmail());
    }

    @Test
    void findUserByEmailFail() { // Encontra um user pelo email. FALHA!

        String userEmail = "alojaoj@hotmail.com";
        Assertions.assertThrows(NoSuchElementException.class, () -> userRepository.findByEmail(userEmail).get());
    }

    @Test
    void removeUser() { // Remove um usuário do banco.

        String email = "almadavic@live.com";
        User user = userRepository.findByEmail(email).get();
        Assertions.assertDoesNotThrow(() -> NoSuchElementException.class);
        userRepository.delete(user);
        Assertions.assertThrows(NoSuchElementException.class, () -> userRepository.findByEmail(email).get());
    }

    @Test
    void findUserByParameterSuccess() { // Verificar se a busca dos usuários pelas roles está correto!

        String roleNameTeacher = "ROLE_TEACHER";
        List<User> teachers = userRepository.findByRolesName(roleNameTeacher);
        boolean teacherRoleValidation = validationRole(roleNameTeacher, teachers);
        Assertions.assertFalse(teacherRoleValidation);
        Assertions.assertEquals(2, teachers.size());

        String roleNamePrincipal = "ROLE_PRINCIPAL";
        List<User> principals = userRepository.findByRolesName(roleNamePrincipal);
        boolean principalRoleValidation = validationRole(roleNamePrincipal, principals);
        Assertions.assertFalse(principalRoleValidation);
        Assertions.assertEquals(1, principals.size());


        String roleNameStudent = "ROLE_STUDENT";
        List<User> students = userRepository.findByRolesName(roleNameStudent);
        boolean studentRoleValidation = validationRole(roleNameStudent, students);
        Assertions.assertFalse(studentRoleValidation);
        Assertions.assertEquals(4, students.size());

    }

    private boolean validationRole(String roleName, List<User> users) { // Método para verificar se a role é válida.
        boolean thereIsAnotherRole = false;
        if (users.stream().anyMatch(user -> !user.getRoles().get(0).getName().equals(roleName))) {
            thereIsAnotherRole = true;
        }
        return thereIsAnotherRole;
    }
}
