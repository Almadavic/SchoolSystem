package application.integrationTests.repositories.builder;


import application.entities.users.Teacher;

public class TeacherBuilder extends UserBuilder { // Classe (FILHA) TeacherBuilder.
    @Override
    public Teacher create() { // Cria um professor.
        return new Teacher(getName(),getEmail(),getPassword(),getClassRoom());
    }
}
