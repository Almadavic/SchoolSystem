package application.integrationTest.repository.builder;


import application.domain.entity.user.Teacher;

public class TeacherBuilder extends UserBuilder { // Classe (FILHA) TeacherBuilder.
    @Override
    public Teacher create() { // Cria um professor.
        return new Teacher(getName(), getEmail(), getPassword(), getClassRoom());
    }
}
