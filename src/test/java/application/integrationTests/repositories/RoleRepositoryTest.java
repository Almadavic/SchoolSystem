package application.integrationTests.repositories;

import application.entities.Role;
import application.integrationTests.repositories.interfaces.GeneralExtendsRepositoryTest;
import application.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.NoSuchElementException;



@SpringBootTest
@Profile(value = "test")
public class RoleRepositoryTest implements GeneralExtendsRepositoryTest { // TESTE DE INTEGRAÇÃO DO

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findRoleByNameSuccess() {

        String roleName = "ROLE_TEACHER";
        Role role = roleRepository.findByName(roleName).get();
        Assertions.assertEquals(roleName,role.getName());
    }

    @Test
    public void findRoleByNameFail() {

        String roleName = "ROLE_TEACHERR";
        Assertions.assertThrows(NoSuchElementException.class,()-> roleRepository.findByName(roleName).get());
    }

    @Test
    @Override
    public void save() {

        String roleName = "ROLE_TEST";
        Role role = new Role(roleName);
        roleRepository.save(role);
        Role roleDataBase = roleRepository.findByName(roleName).get();
        Assertions.assertEquals(roleName,roleDataBase.getName());
    }

}
