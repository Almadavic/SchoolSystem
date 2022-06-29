package application.integrationTest.repository.builder;

import application.entity.user.Student;

public class StudentBuilder extends UserBuilder { // Classe (FILHA) StudentBuilder.

    @Override
    public Student create() { // Cria um estudante.
        return new Student(getName(), getEmail(), getPassword(), getClassRoom());
    }
}
