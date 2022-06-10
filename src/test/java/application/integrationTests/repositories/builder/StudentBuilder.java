package application.integrationTests.repositories.builder;

import application.entities.users.Student;

public class StudentBuilder extends UserBuilder { // Classe (FILHA) StudentBuilder.

    @Override
    public Student create() { // Cria um estudante.
        return new Student(getName(),getEmail(),getPassword(),getClassRoom());
    }
}
