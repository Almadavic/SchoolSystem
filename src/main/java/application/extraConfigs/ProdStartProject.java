package application.extraConfigs;

import application.entities.*;
import application.entities.users.Principal;
import application.repositories.ResponsibilityRepository;
import application.repositories.RoleRepository;
import application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile(value = {"prod"})
public class ProdStartProject implements CommandLineRunner { // Classe chamada apenas quando a aplicação tiver em produção.

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResponsibilityRepository responsibilityRepository;


    @Override
    public void run(String... args)  {

        String passwordEncode = "$2a$10$KT5rbfQTU8103kP6uEmkkO3W8XTc4MFH2peGPuL3sQ3X5ne.kz2oK";



        Principal principal = new Principal("Barbara Borges", "barbara@gmail.com", passwordEncode);
        Registration registration5 = new Registration(Instant.parse("2019-07-21T03:42:10Z"), principal);
        Address principalAddress = new Address("Brasil", "Minas Gerais", "Contagem", principal);

        Responsibility r1 = new Responsibility("Verify what students don't have classes");
        Responsibility r2 = new Responsibility("Verify if there is some teacher with no class");
        Responsibility r3 = new Responsibility("Verify how many students / teachers there are on the system");
        Responsibility r4 = new Responsibility("Has to register classes / teachers / students on the system and also delete them");

        responsibilityRepository.saveAll(Arrays.asList(r1, r2, r3, r4));
        principal.addResponsibility(r1);
        principal.addResponsibility(r2);
        principal.addResponsibility(r3);
        principal.addResponsibility(r4);

        principal.setRegistration(registration5);
        principal.setAddress(principalAddress);


        Role role3 = new Role("ROLE_PRINCIPAL");

        roleRepository.save(role3);

        principal.addRole(role3);

        userRepository.save(principal);


    }
}
