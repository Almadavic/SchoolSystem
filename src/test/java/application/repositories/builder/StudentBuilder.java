package application.repositories.builder;

import application.entities.users.Student;

public class StudentBuilder extends UserBuilder {

    @Override
    public Student create() {
        return new Student(getName(),getEmail(),getPassword(),getClassRoom());
    }
}
