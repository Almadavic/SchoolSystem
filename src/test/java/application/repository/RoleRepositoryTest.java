package application.repository;

import application.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;



@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findRoleByNameSuccess() {

        String roleName = "ROLE_TEACHER";
        Role role = roleRepository.findByName(roleName).get();
        Assertions.assertEquals(role.getName(),roleName);
    }

    @Test
    public void findRoleByNameFail() {

        String roleName = "ROLE_TEACHERR";
        Assertions.assertThrows(NoSuchElementException.class,()-> roleRepository.findByName(roleName).get());
    }

    @Test
    public void saveRole() {

        String roleName = "ROLE_TEST";
        Role role = new Role(roleName);
        roleRepository.save(role);
        Role roleDataBase = roleRepository.findByName(roleName).get();
        Assertions.assertEquals(roleDataBase.getName(),roleName);
    }

}
