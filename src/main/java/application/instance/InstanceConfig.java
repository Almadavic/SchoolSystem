package application.instance;

import application.entity.*;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;


import java.util.Arrays;

@Profile(value = {"test"})
@Configuration
public class InstanceConfig implements CommandLineRunner {


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;


   final private String senhaEncode = "$2a$10$KT5rbfQTU8103kP6uEmkkO3W8XTc4MFH2peGPuL3sQ3X5ne.kz2oK";

    @Override
    public void run(String... args) throws Exception {


        ClassRoom cr1 = new ClassRoom('A');

        Student u1 = new Student("Joao Lacerta", "joao@gmail.com", senhaEncode,cr1);
        Student u2 = new Student ("Victor Almada","almadavic@live.com",senhaEncode,cr1);
        Student u3 = new Student("Renato Tavares","renato@hotmail.com",senhaEncode,cr1);

        Address a1 = new Address("Brasil","Minas Gerais","Belo Horizonte",u1);
        Address a2 = new Address("EUA","Georgia","Atlanta",u2);
        Address a3 = new Address("EUA","Florida","Tampa",u3);

        u1.setAddress(a1);
        u2.setAddress(a2);
        u3.setAddress(a3);



        classRoomRepository.save(cr1);
        studentRepository.saveAll(Arrays.asList(u1,u2,u3));


        Teacher teacher  = new Teacher("Raphael","raphael@gmail.com",senhaEncode,cr1);
        Address a4 = new Address("EUA","Florida","Tampa",teacher);
        teacher.setAddress(a4);

        teacherRepository.save(teacher);

        cr1.setTeacher(teacher);


        Role r1 = new Role("ROLE_STUDENT");
        Role r2 = new Role("ROLE_TEACHER");


        roleRepository.saveAll(Arrays.asList(r1, r2));
        u1.addRole(r1);
        u2.addRole(r1);
        u3.addRole(r1);
        teacher.addRole(r2);

        studentRepository.saveAll(Arrays.asList(u1,u2,u3));

        classRoomRepository.save(cr1);











    }
}
