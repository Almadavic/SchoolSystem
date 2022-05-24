package application.entity;

import application.entity.users.Student;
import application.entity.users.Teacher;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_classes")
public class ClassRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Character letter;
    @Enumerated(EnumType.STRING)
    private ClassShift classShift;

    @OneToOne(mappedBy="classRoom",cascade = CascadeType.ALL)
    private Teacher teacher;

    @OneToMany(mappedBy = "classRoom")
    public List<Student> students = new ArrayList<>();

    public ClassRoom() {

    }

    public ClassRoom(Character letter,ClassShift classShift) {
        this.letter = Character.toUpperCase(letter);
        this.classShift = classShift;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int id) {
        students.remove(id);
    }

    public double getAverageClass() {
        double average = 0;
        for(Student student : students) {
            average = average+student.getReportCard().getAverageStudent();
        }
        return average;
    }

    public Long getId() {
        return id;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }


    public ClassShift getClassShift() {
        return classShift;
    }

    public void setClassShift(ClassShift classShift) {
        this.classShift = classShift;
    }

    public Teacher getTeacher() {
        return teacher;
    }


    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }
}
