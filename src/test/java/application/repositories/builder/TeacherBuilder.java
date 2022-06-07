package application.repositories.builder;


import application.entities.users.Teacher;

public class TeacherBuilder extends UserBuilder {
    @Override
    public Teacher create() {
        return new Teacher(getName(),getEmail(),getPassword(),getClassRoom());
    }
}
