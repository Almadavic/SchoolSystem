package application.UnitTests;

import application.entities.Role;
import application.entities.users.Teacher;
import application.entities.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest { // TESTE DE UNIDADE da entidade User.

    private User user;

    @BeforeAll
    public void instantiateUser() { // Instancia um Usuário antes de começar os testes.
        user = new Teacher("Pedro","pedro@hotmail.com","123456");
    }

    @Test
   public void addRole() { // Teste a adição de uma ROLE.

        Assertions.assertEquals(0,user.getRoles().size()); // Antes de adicionar uma role.
        Role r1 = new Role("ROLE_TESTE1");
        user.addRole(r1);
        Assertions.assertEquals(1,user.getRoles().size()); // Depois de adicionar uma role.
        Role r2 = new Role("ROLE_TESTE2");
        user.addRole(r2);
        Assertions.assertEquals(2,user.getRoles().size()); // Depois de adicionar uma role.
   }




 }
