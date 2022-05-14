package application.instance;

import application.entity.*;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Arrays;

@Configuration
public class InstanceConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;


   final private String senhaEncode = "$2a$10$KT5rbfQTU8103kP6uEmkkO3W8XTc4MFH2peGPuL3sQ3X5ne.kz2oK";

    @Override
    public void run(String... args) throws Exception {


        ClassRoom cr1 = new ClassRoom('A');

        Student u1 = new Student("Joao Lacerta", "joao@gmail.com", senhaEncode,cr1);
        Student u2 = new Student ("Victor Almada","almadavic@live.com",senhaEncode,cr1);
        Student u3 = new Student("Renato Tavares","renato@hotmail.com",senhaEncode,cr1);



        classRoomRepository.save(cr1);
        studentRepository.saveAll(Arrays.asList(u1,u2,u3));


        Teacher teacher  = new Teacher("Raphael","raphael@gmail.com",senhaEncode,cr1);
        cr1.setTeacher(teacher);


        Role r1 = new Role("ROLE_ALUNO");
        Role r2 = new Role("ROLE_PROFESSOR");


        roleRepository.saveAll(Arrays.asList(r1, r2));
        u1.addRole(r1);
        u2.addRole(r1);
        u3.addRole(r1);
        teacher.addRole(r2);

        studentRepository.saveAll(Arrays.asList(u1,u2,u3));

        classRoomRepository.save(cr1);











    }
}
