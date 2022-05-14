package application.instance;

import application.entity.Role;
import application.entity.User;
import application.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import application.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Arrays;

@Configuration
public class InstanceConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        String senhaEncode = "$2a$10$KT5rbfQTU8103kP6uEmkkO3W8XTc4MFH2peGPuL3sQ3X5ne.kz2oK";

        User u1 = new User("Joao", "joao@gmail.com", senhaEncode);
        User u2 = new User("Leticia", "leticia@gmail.com",senhaEncode);

        Role r1 = new Role("ROLE_ALUNO");
        Role r2 = new Role("ROLE_PROFESSOR");

        userRepository.saveAll(Arrays.asList(u1, u2));
        roleRepository.saveAll(Arrays.asList(r1, r2));

        u1.addRole(r1);
        u2.addRole(r2);

        userRepository.saveAll(Arrays.asList(u1, u2));



    }
}
