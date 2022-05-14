package application;

import application.entity.User;
import application.repository.RoleRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Program {

    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }
}
