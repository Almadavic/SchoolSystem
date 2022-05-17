package application;

import application.entity.User;
import application.repository.RoleRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Arrays;

@EnableSpringDataWebSupport
@SpringBootApplication
public class Program {

    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }
}
