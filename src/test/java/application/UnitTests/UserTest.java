package application.UnitTests;

import application.entities.Role;
import application.entities.users.Teacher;
import application.entities.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {

    private User user;

    @BeforeAll
    public void instantiateUser() {
        user = new Teacher("Pedro","pedro@hotmail.com","123456");
    }

    @Test
   public void addRole() {

        Assertions.assertEquals(0,user.getRoles().size());
        Role r1 = new Role("ROLE_TESTE1");
        user.addRole(r1);
        Assertions.assertEquals(1,user.getRoles().size());
        Role r2 = new Role("ROLE_TESTE2");
        user.addRole(r2);
        Assertions.assertEquals(2,user.getRoles().size());
   }




 }
