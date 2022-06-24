package application.config.extraConfig;

import application.domain.entity.ClassRoom;
import application.domain.entity.Responsibility;
import application.domain.entity.Role;
import application.domain.enumerated.ClassShift;
import application.domain.entity.user.Principal;
import application.domain.entity.user.Student;
import application.domain.entity.user.Teacher;
import application.repository.*;
import application.repository.RoleRepository;
import application.domain.vo.AddressVO;
import application.domain.vo.RegistrationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import java.time.Instant;
import java.util.Arrays;


@Configuration
@Profile(value = {"test","dev"})
public class StartProjectConfigurations implements CommandLineRunner { // Essa classe é uma clase separada de configuração, Ela serve para popular o banco de dados!
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @Override
    public void run(String... args) { // Esse método fala que toda vez que o programa iniciar(aplicação for pro ar) em ambiente de DEV E TEST.
        // , esse método vai ser chamado, e também oq tem dentro.
        System.out.println(ApplicationDetails.text());                                // Preste atenção como as associações são feitas no método!

        String passwordEncode = "$2a$10$KT5rbfQTU8103kP6uEmkkO3W8XTc4MFH2peGPuL3sQ3X5ne.kz2oK";

        ClassRoom cr1 = new ClassRoom('A', ClassShift.MORNING);


        Student u1 = new Student("Joao Lacerta", "joao@gmail.com", passwordEncode, cr1);
        Student u2 = new Student("Victor Almada", "almadavic@live.com", passwordEncode, cr1);
        Student u3 = new Student("Renato Tavares", "renato@hotmail.com", passwordEncode, cr1);
        Student u4 = new Student("Marcos Almeida", "marcos@gmail.com", passwordEncode, null);
        Principal principal = new Principal("Barbara Borges", "barbara@gmail.com", passwordEncode);

        RegistrationVO registration1 = new RegistrationVO(Instant.parse("2019-06-20T19:53:07Z"));
        RegistrationVO registration2 = new RegistrationVO(Instant.parse("2019-07-21T03:42:10Z"));
        RegistrationVO registration3 = new RegistrationVO(Instant.parse("2019-07-22T15:21:22Z"));
        RegistrationVO registration4 = new RegistrationVO(Instant.parse("2019-06-20T19:53:07Z"));
        RegistrationVO registration5 = new RegistrationVO(Instant.parse("2019-07-21T03:42:10Z"));

        AddressVO a1 = new AddressVO("Belo Horizonte", "Minas Gerais", "Brasil");
        AddressVO a2 = new AddressVO("Atlanta", "Georgia", "EUA");
        AddressVO a3 = new AddressVO("Tampa", "Florida", "EUA");
        AddressVO a4 = new AddressVO("Tampa", "Florida", "EUA");
        AddressVO principalAddress = new AddressVO("Contagem", "Minas Gerais", "Brasil");

        Responsibility res1 = new Responsibility("Verify what students don't have classes!");
        Responsibility res2 = new Responsibility("Verify if there is some teacher with no class!");
        Responsibility res3 = new Responsibility("Verify how many students / teachers there are on the system!");
        Responsibility res4 = new Responsibility("Has to register classes / teachers / students on the system and also delete them!");

        responsibilityRepository.saveAll(Arrays.asList(res1, res2, res3, res4));
        principal.addResponsibility(res1);
        principal.addResponsibility(res2);
        principal.addResponsibility(res3);
        principal.addResponsibility(res4);

        u1.setRegistration(registration1);
        u2.setRegistration(registration2);
        u3.setRegistration(registration3);
        u4.setRegistration(registration4);
        principal.setRegistration(registration5);
        u1.setAddress(a1);
        u2.setAddress(a2);
        u3.setAddress(a3);
        u4.setAddress(a4);
        principal.setAddress(principalAddress);


        classRoomRepository.save(cr1);
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, principal));

        Teacher teacher1 = new Teacher("Raphael", "raphael@gmail.com", passwordEncode, cr1);
        AddressVO a5 = new AddressVO("Tampa", "Florida", "EUA");
        RegistrationVO registration6 = new RegistrationVO(Instant.parse("2019-07-21T03:42:10Z"));
        teacher1.setAddress(a5);
        teacher1.setRegistration(registration6);

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

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, principal));

        ClassRoom cr2 = new ClassRoom('B', ClassShift.AFTERNOON);

        classRoomRepository.saveAll(Arrays.asList(cr1, cr2));


        Teacher teacher2 = new Teacher("Euni", "euni@gmail.com", passwordEncode);
        AddressVO a6 = new AddressVO("Salvador", "Bahia", "Brasil");
        RegistrationVO registration7 = new RegistrationVO(Instant.parse("2019-07-21T03:42:10Z"));
        teacher2.addRole(role2);
        teacher2.setAddress(a6);
        teacher2.setRegistration(registration7);
        userRepository.save(teacher2);
    }
}