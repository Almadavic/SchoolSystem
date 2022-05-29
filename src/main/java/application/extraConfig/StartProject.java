package application.extraConfig;

import application.entity.*;
import application.entity.users.Principal;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import java.util.Arrays;


@Configuration
@Profile(value = {"test", "dev"})
public class StartProject implements CommandLineRunner { // Essa classe é uma clase separada de configuração!


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponsibilityRepository responsibilityRepository;


    @Override
    public void run(String... args) throws Exception { // Esse método fala que toda vez que o programa iniciar(aplicação for pro ar)
        // , esse método vai ser chamado, e também oq tem dentro.
        System.out.println(applicationInfo());                                // Preste atenção como as associações são feitas no método!

        String senhaEncode = "$2a$10$KT5rbfQTU8103kP6uEmkkO3W8XTc4MFH2peGPuL3sQ3X5ne.kz2oK";

        ClassRoom cr1 = new ClassRoom('A', ClassShift.MORNING);


        Student u1 = new Student("Joao Lacerta", "joao@gmail.com", senhaEncode, cr1);
        Student u2 = new Student("Victor Almada", "almadavic@live.com", senhaEncode, cr1);
        Student u3 = new Student("Renato Tavares", "renato@hotmail.com", senhaEncode, cr1);
        Student u4 = new Student("Marcos Almeida", "marcos@gmail.com", senhaEncode, null);
        Principal principal = new Principal("Barbara Borges", "barbara@gmail.com", senhaEncode);

        Address a1 = new Address("Brasil", "Minas Gerais", "Belo Horizonte", u1);
        Address a2 = new Address("EUA", "Georgia", "Atlanta", u2);
        Address a3 = new Address("EUA", "Florida", "Tampa", u3);
        Address a4 = new Address("EUA", "Florida", "Miami", u4);
        Address principalAddress = new Address("Brasil","Minas Gerais","Contagem",principal);

        Responsibility r1 = new Responsibility("Verify what students doesn't have classes");
        Responsibility r2 = new Responsibility("Verify if there is some teacher with no class");
        Responsibility r3 = new Responsibility("Verify how many students / teachers there are on the system");
        Responsibility r4 = new Responsibility("Has to register classes / teachers / students on the system also delete them");

        responsibilityRepository.saveAll(Arrays.asList(r1,r2,r3,r4));
        principal.addResponsibility(r1);
        principal.addResponsibility(r2);
        principal.addResponsibility(r3);
        principal.addResponsibility(r4);


        u1.setAddress(a1);
        u2.setAddress(a2);
        u3.setAddress(a3);
        u4.setAddress(a4);
        principal.setAddress(principalAddress);



        classRoomRepository.save(cr1);
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4,principal));

        Teacher teacher1 = new Teacher("Raphael", "raphael@gmail.com", senhaEncode, cr1);
        Address a5 = new Address("EUA", "Florida", "Tampa", teacher1);
        teacher1.setAddress(a5);

        userRepository.save(teacher1);

        cr1.setTeacher(teacher1);


        Role role1 = new Role("ROLE_STUDENT");
        Role role2 = new Role("ROLE_TEACHER");
        Role role3 = new Role("ROLE_PRINCIPAL");


        roleRepository.saveAll(Arrays.asList(role1, role2, role3));
        u1.addRole(role1);
        u2.addRole(role1);
        u3.addRole(role1);
        u4.addRole(role1);
        teacher1.addRole(role2);
        principal.addRole(role3);

        userRepository.saveAll(Arrays.asList(u1, u2, u3,u4,principal));

        classRoomRepository.save(cr1);


        Teacher teacher2 = new Teacher("Euni", "euni@gmail.com", senhaEncode);
        Address a6 = new Address("Brasil", "Bahia", "Salvador", teacher2);
        teacher2.addRole(role2);
        teacher2.setAddress(a6);
        userRepository.save(teacher2);


    }

    public String applicationInfo() {
        StringBuilder bd = new StringBuilder();
        bd.append("\n");
        for (int i = 0; i < 70; i++) {
            bd.append("=");
        }
        bd.append("\n");
        bd.append("SCHOOL SYSTEM APPLICATION    ---    VICTOR ALMADA\n");
        for (int i = 0; i < 70; i++) {
            bd.append("=");
        }
        bd.append("\n");
        bd.append("*Java Version - 17 \n");
        bd.append("*Spring Boot Version -  2.6.7  \n");
        bd.append("*Dependencies : \n");
        bd.append("\n");
        bd.append("\n");
        System.out.println("\n");
        for (int i = 0; i < 70; i++) {
            bd.append("=");
        }
        bd.append("\n");
        return bd.toString();
    }
}